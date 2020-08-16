package com.distributed.dao;

import com.distributed.bean.LockBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LockDao {

    @Insert("INSERT INTO locktable(lockName) VALUES (#{lockName})")
    Integer addLock(LockBean lockBean);

    @Select("SELECT * FROM locktable WHERE lockName=#{lockName} for update not wait")
    LockBean tryLock(LockBean lockBean);

    @Select("SELECT * FROM locktable WHERE lockName=#{lockName} for update")
    LockBean lock(LockBean lockBean);


    @Select("SELECT * FROM locktable WHERE lockName=#{lockName}")
    LockBean select(LockBean lockBean);

}
