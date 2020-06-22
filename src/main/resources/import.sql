INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'test01', 'test', '테스트01', 'test01@naver.com');
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'test02', 'test', '테스트02', 'test02@naver.com');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1, 1, '제목_test01', '내용_test01', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (2, 2, '제목_test02', '내용_test02', CURRENT_TIMESTAMP(), 0);