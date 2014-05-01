create database db_web;

use db_web;

/** 创建管理员表 **/
create table if not exists t_admin(
    admin_id        int primary key auto_increment,
    admin_name      nvarchar(100) not null,
    admin_real_name nvarchar(100),
    admin_password  nvarchar(100) not null,
    admin_phone     nvarchar(20),
    admin_mobile    nvarchar(20),
    admin_sex       int not null,/*1 男；0女*/
    admin_email     nvarchar(200),
    create_date     date
);

/** 创建权限表 **/
create table if not exists t_authority (
    authority_id      int primary key auto_increment,
    authority_name    nvarchar(100) not null,
    authority_key     nvarchar(100) not null unique,
    last_modified_by  int, /** 最后修改人ID **/
    create_date       date
);

/** 创建权限组表 **/
create table if not exists t_role(
    role_id    int primary key auto_increment,
    role_name  nvarchar(100) not null,
    role_key   nvarchar(100) not null unique,
    role_level int default 0, /** 权限级别 **/
    role_type  int default 0, /** 权限类别 **/
    role_info  nvarchar(300),
    last_modified_by  int /** 最后修改人ID **/
);


/** 权限映射表 */
create table if not exists t_authority_role_mapping(
    mapping_id      int primary key auto_increment,
    authority_id    int not null,
    role_id        int not null,
    role_key       nvarchar(100) not null,
    authority_key   nvarchar(100) not null,

    foreign key(authority_id) references t_authority(authority_id) on delete no action,
    foreign key(role_id) references t_role(role_id) on delete cascade,
    foreign key(role_key) references t_role(role_key) on delete no action
);

/** 人员权限组映射表 **/
create table if not exists t_admin_role_mapping(
    mapping_id  int primary key auto_increment,
    admin_id    int not null,
    role_id    int not null,
    role_key   nvarchar(100) not null,

    foreign key(admin_id) references t_admin(admin_id) on delete cascade,
    foreign key(role_id) references t_role(role_id) on delete cascade,
    foreign key(role_key) references t_role(role_key) on delete no action
);

/** 查询用户拥有的权限名称 **/

create view t_view_authority_detail(
    admin_id,
    admin_name,
    admin_real_name,
    admin_sex,
    admin_email,
    role_id,
    role_key,
    role_name,
    role_level,
    role_type,
    authority_id,
    authority_key,
    authority_name
) as select ad.admin_id,ad.admin_name,ad.admin_real_name,ad.admin_sex,ad.admin_email,
	   ag.role_id,ag.role_key,ag.role_name,ag.role_level,ag.role_type,
	   au.authority_id,au.authority_key,au.authority_name 
from t_admin ad,
	 t_role ag,
	 t_admin_role_mapping agm,
	 t_authority au,
	 t_authority_role_mapping augm
where ad.admin_id=agm.admin_id 
	and agm.role_id=ag.role_id 
	and ag.role_id=augm.role_id 
	and augm.authority_id=au.authority_id 
	and ag.role_id=augm.role_id 
	and au.authority_id=augm.authority_id;

select * from t_view_authority_detail;