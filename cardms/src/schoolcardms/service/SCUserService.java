package schoolcardms.service;

import schoolcardms.dao.SCUserDao;
import schoolcardms.vo.SCUser;

/**
 * Service即事务处理
 */
public class SCUserService {

    private SCUserDao propDao = new SCUserDao();

    public void save(String name, double money) {
        SCUser pro = new SCUser();
        pro.setName(name);
        pro.setMoney(money);
        propDao.addSCUser(pro);
    }

    public void show() {
        propDao.show();
    }
}
