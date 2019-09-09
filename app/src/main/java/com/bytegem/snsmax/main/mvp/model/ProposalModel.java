package com.bytegem.snsmax.main.mvp.model;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.help.ProposalBean;

public class ProposalModel extends MBaseBean {

    private ProposalBean data;

    public ProposalBean getData() {
        return data;
    }

    public void setData(ProposalBean data) {
        this.data = data;
    }
}
