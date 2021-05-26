package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.MenuVODao;
import com.lyz.demo5.dao.db1.UserDao;
import com.lyz.demo5.dao.db1.UserMenuVODao;
import com.lyz.demo5.model.JwtUser;
import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.MenuVO;
import com.lyz.demo5.model.VO.UserMenuVO;
import com.lyz.demo5.service.AuthService;
import com.lyz.demo5.utils.CustomUtils;
import com.lyz.demo5.utils.JwtTokenUtils;
import com.lyz.demo5.utils.RedisUtils;
import com.lyz.demo5.utils.VCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private static int count =1;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenUtils jwtTokenUtils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserDao userDao;
    @Resource
    private UserMenuVODao userMenuVODao;
    @Resource
    private VCodeUtils vCodeUtils;
    @Resource
    private CustomUtils customUtils;
    @Resource
    private MenuVODao menuVODao;

    /**
     * 验证码有效时间
     */
    @Value("${custom.codeTime}")
    private int codeTime;

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public String login(String name, String pwd) throws Exception {
        //用户验证

       
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, pwd));
            System.out.println("impl login:" + authentication);
            //存储认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);


            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            System.out.println("ID:" + jwtUser.getId());
            Iterator iterator = jwtUser.getAuthorities().iterator();
            System.out.print("用户拥有的权限：");
            while (iterator.hasNext()) {

                System.out.print(((GrantedAuthority) iterator.next()).getAuthority() + "  ");
            }
            System.out.println();

            //生成token
            String str = jwtTokenUtils.generateTokenRole(jwtUser);

            //往redis里存 键为 username  值为 token
            String redisSet = redisUtils.set(jwtUser.getUsername(), str);
            if (redisSet.equals("NO")) {
                System.out.println("redis存值失败");
            }

            return str;



    }

    /**
     * 根据ID修改密码
     *
     * @param id
     * @param pwd
     * @return
     */
    @Override
    public int updatePwdById(String id, String pwd) {
        User user = new User();
        user.setId(id);
        user.setPwd(pwd);
        return userDao.update(user);
    }

    /**
     * 用户注销
     * @return
     */
    @Override
    public long logout() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(null);
        long str = redisUtils.delete(jwtUser.getUsername());
        //security 里面还没有注销，这里只删除了redis里面的缓存
        return str;
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @Override
    public String forgotPwdByEmail(String name, String email) {
        String code = vCodeUtils.getCheckCode(); //生成验证码
        System.out.println("--------------验证码："+code);//发送验证码到邮箱
        String redisSet = redisUtils.set(name+email,code,codeTime);//验证码存入redis  键 name+email 值 code验证码 time 有效时间
        if (redisSet.equals("NO")) {
            System.out.println("redis存值失败");
            return null;
        }

        return "忘记密码成功 验证码:"+code;
    }

    /**
     * 重置密码
     * @param name
     * @param email
     * @param pwd
     * @param code
     * @return
     */
    @Override
    public String resetPwd(String name, String email, String pwd, String code) {
        String redisResult = redisUtils.get(name+email);
        if(redisResult==null||redisResult.equals("")){
            return "redis取值为空";
        }
        if(!redisResult.equals(code)){
            return "验证码错误";
        }
        int result = 0 ;

        if(redisResult.equals(code)){
            redisUtils.delete(name+email);
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            User userResult = userDao.selectByNameEmail(user);
            if(userResult==null){
                return "用户不存在";
            }
            user.setId(userResult.getId());
            user.setPwd(pwd);
            user.setEmail(null);
            user.setName(null);
            result = userDao.update(user);
        }
        if(result>0){
            return "修改成功";
        }

        return "修改失败";
    }

    /**
     * 查询当前用户菜单
     * @return
     */
    @Override
    public MenuVO selectMenuByUserId(String userId) {
        UserMenuVO u =  userMenuVODao.selectMenuByUserId(userId); //根据userId查询user下拥有的menu
        if(u==null||u.getMenuList()==null){
            return null;
        }
        List<Menu> menuList = u.getMenuList();
        MenuVO menuVO = new MenuVO();
        menuVO.setName("根菜单");
        menuVO.setUrl("/www.lyz.com");
        menuVO.setMenuVOList(new ArrayList<>());
        for (Menu m:menuList){

            List<MenuVO> childrenList = menuVODao.selectChildrenById(m.getId());
            System.out.println(childrenList.size());
            if(m.getParentId().equals("0")){

                MenuVO menuVO1 = new MenuVO();
                menuVO1.setId(m.getId());
                menuVO1.setName(m.getName());
                menuVO1.setParentId(m.getParentId());
                menuVO1.setUrl(m.getUrl());
                menuVO1.setMenuVOList(childrenList);
                menuVO.getMenuVOList().add(menuVO1);
            }else {
                List<MenuVO> parentList = menuVODao.selectParentByParentId(m.getParentId());
                List<MenuVO> newList = new ArrayList<>();
                for(int i = 0;i<count;i++){
                    MenuVO menuVO1 = findLastMenu(parentList);
                    addMenuToList(newList,menuVO1);
                }
                count=1;
                MenuVO menuVO1 = new MenuVO();
                menuVO1.setId(m.getId());
                menuVO1.setName(m.getName());
                menuVO1.setParentId(m.getParentId());
                menuVO1.setUrl(m.getUrl());
                menuVO1.setMenuVOList(childrenList);
                addMenuToList(newList,menuVO1);
                //addListToList(newList,childrenList);
                menuVO.getMenuVOList().addAll(newList);
            }
        }
        return menuVO;
    }





    public void addListToList(List<MenuVO> menuVOList ,List<MenuVO> menuVOList2){
        if(menuVOList.size()==0&&menuVOList2!=null){
            menuVOList.addAll(menuVOList2);
        }else if(menuVOList.size()>0&&menuVOList2!=null){
            if(menuVOList.get(0).getMenuVOList()==null){
                menuVOList.get(0).setMenuVOList(new ArrayList<>());
            }
            addListToList(menuVOList.get(0).getMenuVOList(),menuVOList2);
        }
    }
    public void addMenuToList(List<MenuVO> menuVOList ,MenuVO menuVO1){
        if(menuVOList.size()==0&&menuVO1!=null){
            System.out.println(menuVO1.getName());
            count++;
            menuVOList.add(menuVO1);
        }else if(menuVOList.size()>0&&menuVO1!=null){
            if(menuVOList.get(0).getMenuVOList()==null){
                menuVOList.get(0).setMenuVOList(new ArrayList<>());
            }
            addMenuToList(menuVOList.get(0).getMenuVOList(),menuVO1);
        }
    }

    public MenuVO findLastMenu(List<MenuVO> menuVOList1){

        if(menuVOList1!=null&&menuVOList1.size()>0&&menuVOList1.get(0).getMenuVOList()!=null&&menuVOList1.get(0).getMenuVOList().size()==0
        ||menuVOList1!=null&&menuVOList1.size()>0&&menuVOList1.get(0).getMenuVOList()==null){
            if(menuVOList1.get(0).getMenuVOList()==null){
                menuVOList1.get(0).setMenuVOList(new ArrayList<>());
            }
            MenuVO menuVO1 = menuVOList1.get(0);
            System.out.println(menuVO1.getName());
            menuVOList1.clear();
            return menuVO1;
        }else if(menuVOList1!=null&&menuVOList1.size()>0){
            System.out.println("lb else if");

            return findLastMenu(menuVOList1.get(0).getMenuVOList());
        }
        return null;
    }





    @Override
    public List<MenuVO> testbu() {
       return null;
    }






    @Override
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public User selectByNameEmail(User user) {
        return userDao.selectByNameEmail(user);
    }
}
