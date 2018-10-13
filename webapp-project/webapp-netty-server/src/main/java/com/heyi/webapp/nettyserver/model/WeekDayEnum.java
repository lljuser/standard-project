package com.heyi.webapp.nettyserver.model;

public enum WeekDayEnum {
    Mon("一",1){
        @Override
        public WeekDayEnum getNext(){
            return TUS;
        }
    },TUS("二",2){
        @Override
        public WeekDayEnum getNext(){
            return WED;
        }
    }
    ,WED("三",3){
        @Override
        public WeekDayEnum getNext(){
            return THU;
        }
    },THU("四",4){
        @Override
        public WeekDayEnum getNext(){
            return FRI;
        }
    },FRI("五",5){
        @Override
        public WeekDayEnum getNext(){
            return SAT;
        }
    },SAT("六",6){
        @Override
        public WeekDayEnum getNext(){
            return SUN;
        }
    },SUN("七",7){
        @Override
        public WeekDayEnum getNext(){
            return Mon;
        }
    };

    private String cnName;
    private Integer order;

    WeekDayEnum(String cnName,Integer order){
        this.cnName=cnName;
        this.order = order;
    }


    public Integer getOrder(){
        return this.order;
    }

    public String getCnName(){
        return this.cnName;
    }

    public static WeekDayEnum getDayByOrder(Integer order){
        for(WeekDayEnum day: WeekDayEnum.values()){
            if(day.getOrder().equals(order)){
                return day;
            }
        }

        return null;
    }

    public abstract WeekDayEnum getNext();

}
