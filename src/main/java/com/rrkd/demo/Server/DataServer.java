package com.rrkd.demo.Server;

import com.rrkd.demo.Dao.EntityMapper;
import com.rrkd.demo.Entity.EntityText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Tony on 2017/7/11.
 */
@Service
public class DataServer {

    @Autowired(required = true)
    private EntityMapper entityMapper;

    public void contextLoads(EntityText entityText) {
            entityMapper.save(entityText);
        }

    public HashMap testMapper(EntityText entityText){
        HashMap hashMap = entityMapper.callGenCiPropertyValue(entityText);
        return  hashMap;
    }
    public  void findtext(){
        entityMapper.findList();
    }
    }
