-----------------------------------------------------------------------------------------------------------
---						      			Mportal 2015-04-24更新  											---
-----------------------------------------------------------------------------------------------------------
----App启动页
-- Create table
create table X_RMD_APP_STARTMANAGER
(
  id          NUMBER(10) not null,
  app_type    NUMBER(1),
  channel     VARCHAR2(100),
  start_time  DATE,
  status      NUMBER(1),
  create_date DATE default sysdate,
  page_url    VARCHAR2(255),
  page_url_a  VARCHAR2(255)
);
-- Add comments to the columns 
comment on column X_RMD_APP_STARTMANAGER.id
  is '取x_rmd_app_startmanager_SEQ';
comment on column X_RMD_APP_STARTMANAGER.app_type
  is 'APP类型，1:Android|2:iPhone|3:iPad';
comment on column X_RMD_APP_STARTMANAGER.channel
  is '渠道';
comment on column X_RMD_APP_STARTMANAGER.start_time
  is '生效时间';
comment on column X_RMD_APP_STARTMANAGER.status
  is '状态，0:停用|1:启用';
comment on column X_RMD_APP_STARTMANAGER.create_date
  is '创建时间';
comment on column X_RMD_APP_STARTMANAGER.page_url
  is '启动页地址，Android默认、iPad默认、iPhone3.5寸地址';
comment on column X_RMD_APP_STARTMANAGER.page_url_a
  is 'iPhone4寸地址';

-- Create sequence 
create sequence X_RMD_APP_STARTMANAGER_SEQ
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

-- 菜单SQL  id取序列x_rmd_menus_seq.nextval
insert into x_rmd_menus (ID, MENU_NAME, MENU_URL, PARENTID, CREATE_DT, MEMO, STATUS, VERSION)
values (55, 'App启动页管理', '/appStartManager/list', 51, sysdate, 'App启动页管理', 1, 1);

--系统管理员角色增加菜单  id取序列x_rmd_role_menu_seq.nextval
insert into x_rmd_role_menu(id,role_id,menu_id,create_dt,version) values(162,1,55,sysdate,1);


----设备号验证
-- Create table
create table MOBILE_USER_VALIDATE
(
  id          NUMBER(10) not null,
  type        NUMBER(4),
  device_id   VARCHAR2(150),
  limit_times NUMBER(5),
  times       NUMBER(5),
  start_time  DATE,
  stop_time   DATE,
  create_date DATE
);
-- Add comments to the columns 
comment on column MOBILE_USER_VALIDATE.id
  is '取mobile_user_validate_SEQ';
comment on column MOBILE_USER_VALIDATE.type
  is '类型，10:注册|20:忘记密码|30:验证手机';
comment on column MOBILE_USER_VALIDATE.device_id
  is '设备号';
comment on column MOBILE_USER_VALIDATE.limit_times
  is '限制次数';
comment on column MOBILE_USER_VALIDATE.times
  is '次数';
comment on column MOBILE_USER_VALIDATE.start_time
  is '开始时间';
comment on column MOBILE_USER_VALIDATE.stop_time
  is '结束时间';
comment on column MOBILE_USER_VALIDATE.create_date
  is '创建时间';

-- Create sequence 
create sequence MOBILE_USER_VALIDATE_SEQ
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

-- Index
create index idx_mobile_use_validate_id on mobile_user_validate(type,device_id);


-- 用户最后支付记录

  CREATE TABLE XIU_WAP.USER_PAY_PLATFORM 
   (	
   USER_ID VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	CREATE_DATE DATE NOT NULL ENABLE, 
	PAY_PLATFORM VARCHAR2(200 CHAR), 
	UPDATE_DATE DATE, 
	 CONSTRAINT PK_USER_PAY_INFO PRIMARY KEY (USER_ID)
   );
   
-- Add comments to the columns 
comment on column USER_PAY_PLATFORM.USER_ID
  is '用户id';
comment on column USER_PAY_PLATFORM.CREATE_DATE
  is '创建时间';
comment on column USER_PAY_PLATFORM.PAY_PLATFORM
  is '支付方式';
comment on column USER_PAY_PLATFORM.UPDATE_DATE
  is '更新时间';
-----------------------------------------------------------------------------------------------------------
---						      			Mportal 2014-01-09更新  											---
-----------------------------------------------------------------------------------------------------------
-- 新增商品浏览记录表 
CREATE TABLE "XIU_WAP"."BROWSE_GOODS_RECS" 
   (	"ID" NUMBER(10,0) NOT NULL ENABLE, 
	"USER_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"GOODS_SN" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
	"CREATE_DATE" DATE NOT NULL ENABLE, 
	"TERMINAL" NUMBER(1,0) NOT NULL ENABLE
   ) TABLESPACE "XIU_WAP" ;
   COMMENT ON COLUMN "XIU_WAP"."BROWSE_GOODS_RECS"."ID" IS 'BROWSE_GOODS_RECS_SEQ';
   COMMENT ON COLUMN "XIU_WAP"."BROWSE_GOODS_RECS"."TERMINAL" IS '3: m-web  4: and-app 5: iphone-app 6ipad-app ';
-- 新增商品浏览记录序列
    CREATE SEQUENCE  "XIU_WAP"."BROWSE_GOODS_RECS_SEQ"  MINVALUE 100 MAXVALUE 1000000000 INCREMENT BY 1 START WITH 700 CACHE 20 NOORDER  NOCYCLE ;

--创建sequence序列和sync_task_log表   
create table SYNC_TASK_LOG
(
  id             NUMBER(12),
  system_name    VARCHAR2(20),
  procedure_name VARCHAR2(100),
  flag           VARCHAR2(10),
  log_msg        VARCHAR2(100),
  error_msg      VARCHAR2(255),
  create_time    DATE
)
tablespace XIU_WAP
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column SYNC_TASK_LOG.id
  is '日志ID，取sync_task_log_seq';
comment on column SYNC_TASK_LOG.system_name
  is '生成日志的系统名称';
comment on column SYNC_TASK_LOG.procedure_name
  is '执行存储过程名称';
comment on column SYNC_TASK_LOG.flag
  is '执行结果标志(failed、running、success)';
comment on column SYNC_TASK_LOG.log_msg
  is '执行结果信息';
comment on column SYNC_TASK_LOG.error_msg
  is '错误信息';
comment on column SYNC_TASK_LOG.create_time
  is '记录生成时间';
    
    
 -- 特卖卖场数据同步存储过程(存储过程更新)
 create or replace procedure sp_syn_topic_data_task as
   v_position   varchar2(100);    --记录存过内执行位置
   v_msg        varchar2(100);    --错误信息
 begin 
   v_position  := 'insert';
--insert
insert into simple_topic_activity
  (id, activity_id, name, start_time, end_time, order_seq,CONTENT_TYPE,DISPLAY_STYLE)
  select simple_topic_activity_seq.nextval,
         id,
         name,
         trunc(start_date),
         trunc(end_date),
         nvl(order_seq,0),
         (case SUB_TYPE when 6 then 3
         else  SUB_TYPE end) CONTENT_TYPE ,
         (case SUB_TYPE when 6 then 2
         else  1 end) DISPLAY_STYLE 
                  
    from xiu_cms.cms_activity_noregular nor
   where
   nor.id in (select oper.oper_obj_id
                      from xiu_cms.cms_activity_topic_oper oper
                     where oper.oper_type = 'insert'
                       and oper.oper_time >= sysdate - 1/144
                     )
   and not exists (select 1 from simple_topic_activity t where t.activity_id = nor.id);

   v_position  := 'delete';
--delete
delete simple_topic_activity sim
 where sim.activity_id in
       (select oper.oper_obj_id
          from xiu_cms.cms_activity_topic_oper oper
         where oper.oper_time >= sysdate - 1/144
          and  oper.oper_type = 'delete'
         );

   v_position  := 'update';
--update
update simple_topic_activity sim
   set (name, start_time, end_time, order_seq) = (select nor.name,
                                                         trunc(nor.start_date),
                                                         trunc(nor.end_date),
                                                         nvl(nor.order_seq,0)
                                                    from xiu_cms.cms_activity_noregular nor
                                                   where nor.id =
                                                         sim.activity_id
                                                     and nor.id in
                                                         (select distinct oper1.oper_obj_id
                                                            from xiu_cms.cms_activity_topic_oper oper1,
                                                                 simple_topic_activity           sim1
                                                           where oper1.oper_time >= sysdate - 1/144
                                                             and oper1.oper_type = 'update'
                                                             and sim1.modified_by_mobile = 'N'
                                                             and oper1.oper_obj_id =
                                                                 sim1.activity_id))

 where exists (select 1
          from xiu_cms.cms_activity_noregular n2
         where sim.activity_id = n2.id
           and n2.id in (select distinct oper1.oper_obj_id
                           from xiu_cms.cms_activity_topic_oper oper1,
                                simple_topic_activity  sim1
                          where oper1.oper_time >= sysdate - 1/144
                            and oper1.oper_type = 'update'
                            and sim1.modified_by_mobile = 'N'
                            and oper1.oper_obj_id = sim1.activity_id

                         )
              )
;
commit;
 exception
   when others then
     rollback;
     v_msg := SQLCODE||'---'||SQLERRM;
     --插入同步日志
     insert into SYNC_TASK_LOG(id, system_name, procedure_name, flag, log_msg, error_msg, create_time)
     values(SYNC_TASK_LOG_SEQ.Nextval, 'madmin', 'sp_syn_topic_data_task', 'failed', 
            '同步卖场信息失败，失败位置：'||v_position,'错误信息：'||v_msg,sysdate);
     commit;
 end sp_syn_topic_data_task;


-----------------------------------------------------------------------------------------------------------
---						      			Mportal 2014-01-05更新  											---
-----------------------------------------------------------------------------------------------------------
-- 新增商品浏览记录表 
CREATE TABLE "XIU_WAP"."BROWSE_GOODS_RECS" 
   (	"ID" NUMBER(10,0) NOT NULL ENABLE, 
	"USER_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"GOODS_SN" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
	"CREATE_DATE" DATE NOT NULL ENABLE, 
	"TERMINAL" NUMBER(1,0) NOT NULL ENABLE
   ) TABLESPACE "XIU_WAP" ;
   COMMENT ON COLUMN "XIU_WAP"."BROWSE_GOODS_RECS"."ID" IS 'BROWSE_GOODS_RECS_SEQ';
   COMMENT ON COLUMN "XIU_WAP"."BROWSE_GOODS_RECS"."TERMINAL" IS '3: m-web  4: and-app 5: iphone-app 6ipad-app ';
 -- 新增商品浏览记录序列
    CREATE SEQUENCE  "XIU_WAP"."BROWSE_GOODS_RECS_SEQ"  MINVALUE 100 MAXVALUE 1000000000 INCREMENT BY 1 START WITH 700 CACHE 20 NOORDER  NOCYCLE ;

 -- 卖场信息表新增字段
 alter table "XIU_WAP"."SIMPLE_TOPIC_ACTIVITY"
	add(
    	"DISPLAY_STYLE"      NUMBER(1,0) DEFAULT 1 NOT NULL ENABLE,
    	"BANNER_PIC"         VARCHAR2(255 BYTE),
    	"DISPLAY_BANNER_PIC" CHAR(1 BYTE) DEFAULT 'Y',
    	"DISCOUNT"           NUMBER(3,2)
   ); 
   
 -- 特卖卖场数据同步存储过程
 CREATE OR REPLACE
PROCEDURE sp_syn_topic_data_task
AS
BEGIN
  --insert
  INSERT
  INTO simple_topic_activity
    (
      id,
      activity_id,
      name,
      start_time,
      end_time,
      order_seq,
      CONTENT_TYPE
    )
  SELECT simple_topic_activity_seq.nextval,
    id,
    name,
    TRUNC(start_date),
    TRUNC(end_date),
    order_seq,
    (
    CASE SUB_TYPE
      WHEN 6
      THEN 3
      ELSE SUB_TYPE
    END) CONTENT_TYPE
  FROM xiu_cms.cms_activity_noregular nor
  WHERE nor.id IN
    (SELECT oper.oper_obj_id
    FROM xiu_cms.cms_activity_topic_oper oper
    WHERE oper.oper_type = 'insert'
    AND oper.oper_time   > sysdate - 1/144
    );
  --delete
  DELETE simple_topic_activity sim
  WHERE sim.activity_id IN
    (SELECT oper.oper_obj_id
    FROM xiu_cms.cms_activity_topic_oper oper
    WHERE oper.oper_time > sysdate - 1/144
    AND oper.oper_type   = 'delete'
    );
  --update
  UPDATE simple_topic_activity sim
  SET
    (
      name,
      start_time,
      end_time,
      order_seq
    )
    =
    (SELECT nor.name,
      TRUNC(nor.start_date),
      TRUNC(nor.end_date),
      nor.order_seq
    FROM xiu_cms.cms_activity_noregular nor
    WHERE nor.id = sim.activity_id
    AND nor.id  IN
      (SELECT DISTINCT oper1.oper_obj_id
      FROM xiu_cms.cms_activity_topic_oper oper1,
        simple_topic_activity sim1
      WHERE oper1.oper_time       > sysdate - 1/144
      AND oper1.oper_type         = 'update'
      AND sim1.modified_by_mobile = 'N'
      AND oper1.oper_obj_id       = sim1.activity_id
      )
    )
  WHERE EXISTS
    (SELECT 1
    FROM xiu_cms.cms_activity_noregular n2
    WHERE sim.activity_id = n2.id
    AND n2.id            IN
      (SELECT DISTINCT oper1.oper_obj_id
      FROM xiu_cms.cms_activity_topic_oper oper1,
        simple_topic_activity sim1
      WHERE oper1.oper_time       > sysdate - 1/144
      AND oper1.oper_type         = 'update'
      AND sim1.modified_by_mobile = 'N'
      AND oper1.oper_obj_id       = sim1.activity_id
      )
    ) ;
  COMMIT;
END sp_syn_topic_data_task;
    
    
-----------------------------------------------------------------------------------------------------------
---						      			Mportal 2014-12-22更新  											---
-----------------------------------------------------------------------------------------------------------
  CREATE TABLE "XIU_WAP"."X_TEMP_QUESTION_GAME" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"NAME" VARCHAR2(200 BYTE), 
	"IMGURL" VARCHAR2(500 BYTE) NOT NULL ENABLE, 
	"SCORE" NUMBER(*,0) DEFAULT 1, 
	"SEX" CHAR(1 BYTE) DEFAULT 1 NOT NULL ENABLE, 
	"QUESTIONNO" NUMBER(*,0) DEFAULT 1 NOT NULL ENABLE, 
	 CONSTRAINT "X_TEMP_QUESTION_GAME_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "XIU_WAP"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "XIU_WAP" ;
 

  CREATE TABLE "XIU_WAP"."X_TEMP_QUESTION_PLAYER" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"MAXNUMBER" NUMBER NOT NULL ENABLE, 
	"REALLYNUMBER" NUMBER NOT NULL ENABLE, 
	 CONSTRAINT "X_TEMP_GAME_PLAYER_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "XIU_WAP"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "XIU_WAP" ;
 
REM INSERTING into X_TEMP_QUESTION_GAME
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (1,'1shirt','http://1.xiustatic.com/m/h5questiongame/question_items/man/1shirt.jpg',1,'1',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (2,'2shirt','http://1.xiustatic.com/m/h5questiongame/question_items/man/2shirt.jpg',2,'1',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (3,'3shirt','http://1.xiustatic.com/m/h5questiongame/question_items/man/3shirt.jpg',3,'1',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (4,'4shirt','http://1.xiustatic.com/m/h5questiongame/question_items/man/4shirt.jpg',5,'1',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (5,'1trousers','http://1.xiustatic.com/m/h5questiongame/question_items/man/1trousers.jpg',1,'1',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (6,'2trousers','http://1.xiustatic.com/m/h5questiongame/question_items/man/2trousers.jpg',2,'1',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (7,'3trousers','http://1.xiustatic.com/m/h5questiongame/question_items/man/3trousers.jpg',3,'1',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (8,'4trousers','http://1.xiustatic.com/m/h5questiongame/question_items/man/4trousers.jpg',5,'1',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (9,'1shoes','http://1.xiustatic.com/m/h5questiongame/question_items/man/1shoes.jpg',1,'1',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (10,'2shoes','http://1.xiustatic.com/m/h5questiongame/question_items/man/2shoes.jpg',2,'1',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (11,'3shoes','http://1.xiustatic.com/m/h5questiongame/question_items/man/3shoes.jpg',3,'1',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (12,'4shoes','http://1.xiustatic.com/m/h5questiongame/question_items/man/4shoes.jpg',5,'1',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (13,'1map','http://1.xiustatic.com/m/h5questiongame/question_items/man/1map.jpg',1,'1',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (14,'2map','http://1.xiustatic.com/m/h5questiongame/question_items/man/2map.jpg',2,'1',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (15,'3map','http://1.xiustatic.com/m/h5questiongame/question_items/man/3map.jpg',3,'1',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (16,'4map','http://1.xiustatic.com/m/h5questiongame/question_items/man/4map.jpg',5,'1',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (17,'1bag','http://1.xiustatic.com/m/h5questiongame/question_items/man/1bag.jpg',1,'1',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (18,'2bag','http://1.xiustatic.com/m/h5questiongame/question_items/man/2bag.jpg',2,'1',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (19,'3bag','http://1.xiustatic.com/m/h5questiongame/question_items/man/3bag.jpg',3,'1',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (20,'4bag','http://1.xiustatic.com/m/h5questiongame/question_items/man/4bag.jpg',5,'1',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (21,'1watch','http://1.xiustatic.com/m/h5questiongame/question_items/man/1watch.jpg',1,'1',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (22,'2watch','http://1.xiustatic.com/m/h5questiongame/question_items/man/2watch.jpg',2,'1',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (23,'3watch','http://1.xiustatic.com/m/h5questiongame/question_items/man/3watch.jpg',3,'1',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (24,'4watch','http://1.xiustatic.com/m/h5questiongame/question_items/man/4watch.jpg',5,'1',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (25,'1coat','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1coat.jpg',1,'0',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (26,'2coat','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2coat.jpg',2,'0',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (27,'3coat','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3coat.jpg',3,'0',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (28,'4coat','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4coat.jpg',5,'0',1);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (29,'1skirt','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1skirt.jpg',1,'0',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (30,'2skirt','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2skirt.jpg',2,'0',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (31,'3skirt','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3skirt.jpg',3,'0',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (32,'4skirt','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4skirt.jpg',5,'0',2);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (33,'1shoes','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1shoes.jpg',1,'0',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (34,'2shoes','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2shoes.jpg',2,'0',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (35,'3shoes','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3shoes.jpg',3,'0',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (36,'4shoes','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4shoes.jpg',5,'0',3);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (37,'1map','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1map.jpg',1,'0',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (38,'2map','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2map.jpg',2,'0',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (39,'3map','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3map.jpg',3,'0',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (40,'4map','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4map.jpg',5,'0',4);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (41,'1necklace','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1necklace.jpg',1,'0',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (42,'2necklace','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2necklace.jpg',2,'0',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (43,'3necklace','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3necklace.jpg',3,'0',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (44,'4necklace','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4necklace.jpg',5,'0',5);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (45,'1bag','http://1.xiustatic.com/m/h5questiongame/question_items/woman/1bag.jpg',1,'0',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (46,'2bag','http://1.xiustatic.com/m/h5questiongame/question_items/woman/2bag.jpg',2,'0',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (47,'3bag','http://1.xiustatic.com/m/h5questiongame/question_items/woman/3bag.jpg',3,'0',6);
Insert into X_TEMP_QUESTION_GAME (ID,NAME,IMGURL,SCORE,SEX,QUESTIONNO) values (48,'4bag','http://1.xiustatic.com/m/h5questiongame/question_items/woman/4bag.jpg',5,'0',6);

REM INSERTING into X_TEMP_QUESTION_PLAYER
Insert into X_TEMP_QUESTION_PLAYER (ID,MAXNUMBER,REALLYNUMBER) values (1,150000,0);
