-- 1. 데이터베이스(계정) 생성 이미 만들어져 있으면 테이블 생성으로 이동
show user;
-- 12c 버전 부터는 아래 문장 실행후 계정 생성 
 ALTER SESSION SET "_ORACLE_SCRIPT"=true; 
 
 -- 계정(데이터베이스) 생성
create user board identified by 1234;  

-- 새로 만든 계정(데이터베이스)에 권한 부역
grant connect to board;  -- DB 연결권한(권한취소 : revoke connect  from company)
grant create table to board;  -- 테이블 생성권한
grant create view to board; -- view  생성권한
commit;

-- 사용자 테이블 스페이스 사용 권한 부여
alter user board default tablespace users quota unlimited on users;

-- board 계정으로 접속 가능해짐. board 계정 접속 경로 설정

-- 7. sys 계정을 닫고 새로 만든 ky 계정으로 다시 접속
show user;
-- USER이(가) "BOARD"입니다.

-- board 데이터베이스에 board 테이블 생성
--drop table simple_board;
create table simple_board (
     no number(5),
     title varchar(50)  not null,
     content  varchar(1000) not null,
     writer varchar(50)  not null,
     hit number (5) default 0,
     regdate date default sysdate
       );
-- primary key constraint 추가
alter table simple_board add constraint pk_board primary key(no);
-- 시퀀스 생성(번호가 자동으로 1씩 증가)
create sequence seq_simple_board increment by 1 start with 1;
--drop sequence seq_simple_board;

--샘플 데이터 저장
insert into simple_board(no, title, content, writer, hit) values(seq_simple_board.nextval, 'title1', 'content1', 'writer1', 0);
insert into simple_board(no, title, content, writer, hit) values(seq_simple_board.nextval, 'title2', 'content2', 'writer2', 0);
insert into simple_board(no, title, content, writer, hit) values(seq_simple_board.nextval, 'title2', 'content2', 'writer2', 0);
commit;

select no, title, content, writer, hit, regdate from simple_board order by regdate desc;

create table members(
     id varchar2(20) primary key,
     pwd varchar2(20) not null,
     name varchar2(50) not null,
     email varchar2(100),
     joindate date default sysdate );

insert into members values ('dream', '1234', '이정미', 'dream@google.com', to_date('20190516', 'yyyy-mm-dd'));
insert into members values ('hong', '4567', '홍순남', 'hong@google.com', to_date('20170228', 'yyyy-mm-dd'));
insert into members values ('jsupark', '2596', '박준수', 'jsupark@google.com', to_date('20200120', 'yyyy-mm-dd'));
commit;
select * from members

-- 페이징 답변형 게시판을 위한 board table 생성
-- board 데이터베이스에 board 테이블 생성
-- drop table board;
create table board (
     no number(5) primary key,
     title varchar(50)  not null,
     content  varchar(1000) not null,
     id  varchar2(20) references members(id),
     hit number (5) default 0,
     regdate date default sysdate,
     reply_ref number(5) default 0 not null,
     reply_lev number(5) default 0  not null,
     reply_seq number(5) default 0  not null
      );
-- primary key constraint 추가(테이블생성후 추가할경우에 사용)
--alter table board add constraint pk_board primary key(no);

-- 외래키 foreign key constraint 추가(테이블생성후 추가할경우에 사용)
-- alter table board add constraint fk_board foreign key(id) references members(id);


-- 시퀀스 생성(번호가 자동으로 1씩 증가)
--drop sequence seq_board;
create sequence seq_board increment by 1 start with 1;
commit;

--샘플 데이터 저장
insert into board(no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq) values(seq_board.nextval, 'title1', 'content1', '1', 0, sysdate, seq_board.nextval, 0, 0);
insert into board(no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq) values(seq_board.nextval, 'title2', 'content2', 'dream', 0, sysdate, seq_board.nextval, 0, 0);

select no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq from board;
commit;

select id, pwd, name, email, to_char(joindate, 'yyyy-mm-dd') as joindate 
from members
where id = 'abcd';

-- 오라클 rownum
select no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq
from(
     select rownum rnum, no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq
     from (
          select no, title, content, id, hit, regdate, reply_ref, reply_lev, reply_seq
          from board
          order by reply_ref desc, reply_seq asc
     )
)
where rnum >= 1 and rnum <=10

update board set reply_seq= reply_seq + 1
where reply_ref = 34 and reply_seq  > 0