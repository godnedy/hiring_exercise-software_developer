
drop table if exists ANALYSIS_RESULT_TITLE;
drop table if exists ANALYSIS_RESULT;
drop table if exists ITEM;

create table ITEM
(
  ID long not null,
  FEED_ID int not null,
  TITLE varchar(500) not null,
  LINK clob not null,
  ROLE nvarchar(10) not null,
  primary key (ID)
);

create table ANALYSIS_RESULT
(
    RESULT_ID long not null,
    ANALYSIS_ID varchar(36) not null,
    WORD varchar(50) not null,
    FREQUENCY int not null,
    primary key (RESULT_ID)
);

create index IDX_ANALYSIS_RESULT_ANALYSIS_ID on ANALYSIS_RESULT(ANALYSIS_ID);

create table ANALYSIS_RESULT_ITEM
(
    RESULT_ID long not null,
    ITEM_ID varchar(36) not null,
    primary key (RESULT_ID, ITEM_ID),
    constraint ANALYSIS_RESULT_ITEM_RESULT_FK FOREIGN KEY (RESULT_ID) references ANALYSIS_RESULT (RESULT_ID) ,
    constraint ANALYSIS_RESULT_ITEM_ITEM_FK FOREIGN KEY (ITEM_ID) references ITEM (ID)
);

