package com.bytegem.snsmax.main.app.mvc.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class AreaBean implements IPickerViewData {

    public String name;
    public List<City> city;

    public static class City {
        public String name;
        public List<String> area;

    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
