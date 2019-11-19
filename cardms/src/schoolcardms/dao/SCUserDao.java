package schoolcardms.dao;

import schoolcardms.util.SCUserList;
import schoolcardms.vo.SCUser;

public class SCUserDao {

    //添加SCUser
    public void addSCUser(SCUser scUser) {
        SCUserList.props.add(scUser);
    }

    public void show() {
        for (SCUser prop : SCUserList.props) {
            System.out.println(prop);
        }
    }
}
