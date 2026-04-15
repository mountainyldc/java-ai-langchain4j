package com.atguigu.java.ai.langchain4j.service;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AppointmentService extends IService<Appointment> {
    /**
     * getOne：根据条件查询单条数据
     * @param appointment
     * @return
     */
    Appointment getOne(Appointment appointment);
}