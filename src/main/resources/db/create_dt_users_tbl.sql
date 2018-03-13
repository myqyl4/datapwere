TRUNCATE TABLE HR.DTUSER REUSE STORAGE;

DELETE FROM HR.DTUSER;

commit;


DROP TABLE HR.DTUSER  cascade constraints purge;

commit;



CREATE TABLE HR.DTUSER (
         id          NUMBER(20) NOT NULL,
         guid      VARCHAR2(50) NOT NULL,
         firstname      VARCHAR2(50) NOT NULL,
         lastname      VARCHAR2(50) NOT NULL,
         fullname      VARCHAR2(100) NOT NULL,
         age        NUMBER(5),
         birthday   VARCHAR2(15),
         email      VARCHAR2(150),
         gender      VARCHAR2(20),
         filename  VARCHAR2(255),
         phone      VARCHAR2(20),
         street        VARCHAR2(150),
         state        VARCHAR2(50),
         zip        VARCHAR2(15),
         createdBy   VARCHAR2(50),
         created   DATE DEFAULT (sysdate),
         modifiedBy   VARCHAR2(50),
         modified   DATE DEFAULT (sysdate)
         );
         

commit;


ALTER TABLE HR.DTUSER  ADD (CONSTRAINT HR.DTUSER _pk PRIMARY KEY (id));

commit;

CREATE SEQUENCE HR.DTUSER_seq;

commit;


CREATE OR REPLACE TRIGGER HR.DTUSER_trggr 
BEFORE INSERT ON HR.DTUSER  
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT HR.DTUSER_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

ALTER TABLE HR.DTUSER  MODIFY dtid GENERATED ALWAYS AS IDENTITY;

commit;



ALTER TABLE HR.DTUSER  ADD filename varchar2(255);

commit;


ALTER SEQUENCE HR.DTUSER _seq INCREMENT BY 1 MINVALUE 0;
