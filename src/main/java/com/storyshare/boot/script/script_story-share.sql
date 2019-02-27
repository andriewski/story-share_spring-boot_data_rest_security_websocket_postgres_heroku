-- CREATE TABLE T_USER (
--   F_ID       SERIAL PRIMARY KEY NOT NULL,
--   F_NAME     VARCHAR(60)        NOT NULL,
--   F_EMAIL    VARCHAR(255)       NOT NULL,
--   F_AVATAR   VARCHAR(255),
--   F_PASSWORD VARCHAR(50)        NOT NULL,
--   F_STATUS   VARCHAR(10) DEFAULT 'active',
--   F_ROLE     CHAR(7)     DEFAULT 'user'
-- );
--
-- CREATE TABLE T_MESSAGE (
--   F_ID                  SERIAL PRIMARY KEY NOT NULL,
--   F_TEXT                TEXT,
--   F_DATE                TIMESTAMP(3),
--   F_DELETED_BY_SENDER   BOOLEAN DEFAULT FALSE,
--   F_DELETED_BY_RECEIVER BOOLEAN DEFAULT FALSE,
--   F_SENDER_ID           INT                NOT NULL,
--   CONSTRAINT FK_SENDER_ID FOREIGN KEY (F_SENDER_ID)
--   REFERENCES T_USER (F_ID),
--   F_RECEIVER_ID         INT                NOT NULL,
--   CONSTRAINT FK_RECEIVER_ID FOREIGN KEY (F_RECEIVER_ID)
--   REFERENCES T_USER (F_ID)
-- );
--
-- CREATE TABLE T_POST (
--   F_ID      SERIAL PRIMARY KEY NOT NULL,
--   F_TEXT    TEXT,
--   F_DATE    TIMESTAMP(3),
--   F_USER_ID INT                NOT NULL,
--   CONSTRAINT FK_USER_ID FOREIGN KEY (F_USER_ID)
--   REFERENCES T_USER (F_ID),
--   F_PICTURE VARCHAR(255)       NOT NULL
-- );
--
-- CREATE TABLE T_COMMENT (
--   F_ID      SERIAL PRIMARY KEY NOT NULL,
--   F_USER_ID INT             NOT NULL,
--   CONSTRAINT FK_USER_COMMENT_ID FOREIGN KEY (F_USER_ID)
--   REFERENCES T_USER (F_ID),
--   F_POST_ID INT             NOT NULL,
--   CONSTRAINT FK_POST_COMMENT_ID FOREIGN KEY (F_POST_ID)
--   REFERENCES T_POST (F_ID)
--   ON DELETE CASCADE,
--   F_TEXT    TEXT,
--   F_DATE    TIMESTAMP(3)
-- );
--
-- CREATE TABLE T_LIKE (
--   F_POST_ID INT NOT NULL,
--   CONSTRAINT FK_POST_ID FOREIGN KEY (F_POST_ID)
--   REFERENCES T_POST (F_ID)
--   ON DELETE CASCADE,
--   F_USER_ID INT NOT NULL,
--   CONSTRAINT FK_USER_LIKE_ID FOREIGN KEY (F_USER_ID)
--   REFERENCES T_USER (F_ID)
-- );

INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, F_ROLE, f_version)
VALUES ('Mark',
        'mark@tut.by',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Vladimir_Putin_%282017-07-08%29.jpg/250px-Vladimir_Putin_%282017-07-08%29.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS',
        'boss', 0);
INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, F_ROLE, f_version)
VALUES ('Denis',
        'denis@tut.by',
        'https://www.interfax.ru/ftproot/textphotos/2015/01/26/trump700.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS',
        'admin', 0);
INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, F_ROLE, f_version)
VALUES ('Grisha',
        'grisha@tut.by',
        'https://www.5.ua/media/pictures/original/29015.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS',
        'admin', 0);
INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, f_version)
VALUES ('Vladimir',
        'vladimir@tut.by',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Architect_Schuko_Vladimir_Alekseyevich.jpg/220px-Architect_Schuko_Vladimir_Alekseyevich.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS', 0);
INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, f_version)
VALUES ('Nikolai',
        'nikolai@tut.by',
        'https://cdna.artstation.com/p/assets/images/images/003/488/934/20160919030802/smaller_square/nikolai-lockertsen-image.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS', 0);
INSERT INTO T_USER (F_NAME, F_EMAIL, F_AVATAR, F_PASSWORD, f_version)
VALUES ('Василиса',
        'v@tut.by',
        'https://24smi.org/public/media/235x307/person/2018/01/08/lyvgm9v9cod4-vasilisa-premudraia.jpg',
        '$2a$10$MLjPrjDcmMAGltH4vY1t7eFuOTQElXtSOidopnb3gvnfT9lfeCuHS', 0);

INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Ку', '1991-02-20 19:20:42.001', 1, 2);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Ky-ky', '1991-02-20 00:00:00.002', 2, 1);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Как дела?', '1991-02-21 01:10:01.003', 1, 2);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Дороу, Марк! Это Гриша', '1991-02-21 01:11:01.003', 3, 1);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Все пучком! :)', '1991-02-21 02:10:01.001', 2, 1);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Удачи!', '1991-02-22 12:10:01.005', 1, 2);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('И тебе удачи!', '1992-02-22 12:10:01.100', 2, 1);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Спасибо!', '1993-02-22 12:10:01.004', 1, 2);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Привет, Володя) Это Марк', '1989-02-22 12:10:01.001', 1, 4);
INSERT INTO T_MESSAGE (F_TEXT, F_DATE, F_SENDER_ID, F_RECEIVER_ID)
VALUES ('Привет, Коля! Это Марк', '1989-02-22 12:10:01.001', 1, 5);

INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Привет всем!', '1993-02-22 12:10:01.001', 1, 'https://i.ytimg.com/vi/uIDM3TK-_0I/maxresdefault.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('И тебе привет!', '1994-02-22 12:10:01.002', 2, 'http://memesmix.net/media/created/r483oc.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Вот мой кот',
        '1995-02-22 12:10:01.003',
        3,
        'http://mignews.com.ua/modules/news/images/articles/changing/19594650-kot-so-slomannym-pozvonochnikom-porazil.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Ку, ребзи!', '1996-02-22 12:10:01.004', 1, 'https://i.ytimg.com/vi/LMByI5RHhfk/maxresdefault.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Дороу, это Гриша', '1997-02-22 12:10:01.005', 3, 'http://img.1001mem.ru/posts_temp/17-10-12/3919394.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('What is Lorem Ipsum?
Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        '1997-02-22 13:10:01.005',
        1,
        'https://st2.depositphotos.com/2001755/5408/i/450/depositphotos_54081723-stock-photo-beautiful-nature-landscape.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 14:10:01.005',
        2,
        'http://1.bp.blogspot.com/_YU-T6HyQp7s/Sw_uLa-upvI/AAAAAAAAAE8/6jwP8_y4Sw8/s1600/Windows_XP_Wallpaper_1024_768_8.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 14:15:01.005',
        3,
        'http://m-a-s-o.narod.ru/wp/nature/01/nature_060.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 15:10:01.005',
        4,
        'https://cdn.hipwallpaper.com/i/9/84/GXSaIJ.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 16:10:01.005',
        5,
        'http://ru.naturewallpaperfree.com/gory-poberezhiy/priroda-oboi-1024x768-3414-a540e583.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 17:10:01.005',
        6,
        'http://ru.naturewallpaperfree.com/koshki/priroda-oboi-1024x768-4069-bf38815d.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 18:18:01.005',
        1,
        'http://bergoiata.org/fe/felins/-EvilBlackPantherRoar-wall-1024x768-TR.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 19:10:01.005',
        2,
        'https://static.totalwararena.com/original_images/TWA_wallpaper_august_2018_triarii_1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 20:10:01.005',
        3,
        'http://www.dan-dare.org/FreeFun/Images/CartoonsMoviesTV/IceAge2Wallpaper1024.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 21:10:01.005',
        4,
        'https://images.wallpaperscraft.ru/image/tunnel_podsvetka_dvizhenie_130807_300x255.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 22:10:01.005',
        5,
        'http://studentsites.woodlandschools.org/2015/farrisc15/Web%20Design%201/Projects/FinalProject/1024x768%20Pics/img40.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '1997-02-22 23:10:01.005',
        6,
        'https://www.filmtekercs.hu/wp-content/uploads/2015/01/how_to_train_your_dragon_hi-res_still_02_-_960.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 12:10:01.005',
        1,
        'https://i.imgur.com/0Ysdzig.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 13:10:01.005',
        2,
        'http://a2goos.com/data_images/models/nissan-silvia/nissan-silvia-07.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 14:10:01.005',
        3,
        'http://www.petr-fevronia.ru/uploads/page/0fa5cc4362988a0e9c1764f6526c1b72021cbcdf.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 15:10:01.005',
        4,
        'http://www.bfoto.ru/oboi/646_1024x768_bfotoru.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 16:10:01.005',
        5,
        'http://art.mau.ru/wall/1024768/wallcat002.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 17:10:01.005',
        6,
        'http://bojack.nf-fan.tv/pictures/bojack_43--1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 18:10:01.005',
        1,
        'https://wpapers.ru/wallpapers/nature/Mountains/3020/PREV_%D0%93%D0%BE%D1%80%D1%8B.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-01 18:10:01.005',
        2,
        'https://images.wallpaperscraft.ru/image/devushka_anime_trava_lezhit_art_107288_300x255.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-02 12:10:01.005',
        3,
        'http://www.korova006.ru/img/wallpaper_korova006_01_1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-03 12:10:01.005',
        4,
        'https://www.gamersdecide.com/sites/default/files/styles/news_images/public/content-images/news/2016/10/14/top-10-best-skyrim-wallpapers/the_elder_scrolls_v_skyrim.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-04 12:10:01.005',
        5,
        'http://4.bp.blogspot.com/_W1VhkUCefIk/S8zsbzdKLPI/AAAAAAAAAEk/0qoerDkngIo/s1600/Drawn_wallpapers_flowers.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-05 12:10:01.005',
        6,
        'http://dinopedia.ru/img/wallpapers/8.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-06 12:10:01.005',
        1,
        'https://desktopmania.ru/pics/00/95/12/DesktopMania.ru-95128-300x224.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-07 12:10:01.005',
        2,
        'https://www.selters-water.ru/wp-content/gallery/wallpapers-5/selters-wallpaper-5-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-08 12:10:01.005',
        3,
        'http://friendak.com/3d_1024x768_wallpaper_17.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-09 12:10:01.005',
        4,
        'http://adventuretime.cn-fan.ru/pictures/Adventure-Time_90--1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-01-10 12:10:01.005',
        5,
        'http://www.fonstola.ru/large/201504/171468.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 13:10:01.005',
        6,
        'http://a2goos.com/data_images/galleryes/hyundai-getz/hyundai-getz-11.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 14:10:01.005',
        1,
        'https://images.wallpaperscraft.ru/image/ogni_iarkie_boke_130801_300x255.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 15:10:01.005',
        2,
        'https://stolenprincess.com/uploads/assets/images/1024%D1%85768_kiev.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 16:10:01.005',
        3,
        'http://eu.blizzard.com/static/_images/games/wow/wallpapers/wall2/wall2-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 17:10:01.005',
        4,
        'http://janosh.lv/wp-content/uploads/selfhealing_1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 18:10:01.005',
        5,
        'http://spacegid.com/wp-content/uploads/2016/04/Galaktika-Andromedyi-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-02-01 19:10:01.005',
        6,
        'https://www.oneindia.com/img/2016/02/20-1455964873--universe.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 13:10:01.005',
        1,
        'http://iv-orthodox.pp.ua/wp/WP_Ostroyskiy07-1024.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 14:10:01.005',
        2,
        'http://easeetrip.com/wp-content/uploads/2015/07/easeetrip-menorca-velero-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 16:10:01.005',
        3,
        'http://porodas.ru/star.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 17:10:01.005',
        4,
        'https://www.setaswall.com/wp-content/uploads/2018/11/Funny-Wallpaper-04-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 18:10:01.005',
        5,
        'https://aw.cdn.gmru.net/ms/cca4951fa6b82735e42b13b13e789109.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 19:10:01.005',
        6,
        'https://www.katarocks.com/wp-content/uploads/2017/05/KR_9023-1-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 20:10:01.005',
        1,
        'http://dinopedia.ru/img/wallpapers/6.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 21:10:01.005',
        2,
        'http://bmw-mclub.ru/img/wallpapers/1024x768/wallpapers_1024_768_02.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 22:10:01.005',
        3,
        'http://elligo.ru/wp-content/uploads/2012/06/IMG_0683-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-03-01 23:10:01.005',
        4,
        'http://ellibr.ucoz.ru/oboi/100/2a.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-01 10:10:01.005',
        5,
        'http://www.gandex.ru/upl/oboi/gandex.ru-19782_7813_dodge-viper-tuning-sport.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-02 10:10:01.005',
        6,
        'http://www.wallpapermania.eu/images/lthumbs/2015-05/7518_Beautiful-yellow-Audi-Car-HD-beautiful-wallpaper.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-03 10:10:01.005',
        1,
        'http://www.unikturs.com/wp-content/uploads/2018/02/ford-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-04 10:10:01.005',
        2,
        'https://cameralabs.org/media/cameralabs/images/Tanya/August/30.08/audi-q5-taillights-wallpapers_34033_1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-05 10:10:01.005',
        3,
        'http://one-way.ru/walls/dl.php?walls_id=1192');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-06 10:10:01.005',
        4,
        'http://art.mau.ru/wall/1024768/wallcat003.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-04-07 10:10:01.005',
        5,
        'http://forjatmd.ru/wp-content/uploads/2017/04/DSC_0291-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
        '2018-05-01 10:10:01.005',
        6,
        'https://getbg.net/upload/full/www.GetBg.net_Nature___Volcanoes_Lava_flow_from_the_volcano_044912_.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D', '2018-05-02 10:10:01.005', 1, 'http://guyver-world.ru/pics/w/w13-1024x768.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D', '2018-05-03 10:10:01.005', 2, 'http://dinopedia.ru/img/wallpapers/2.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D',
        '2018-05-04 10:10:01.005',
        3,
        'https://i18.kanobu.ru/r/7111b1bba2489c39539d07e28299da62/1040x-/u.kanobu.ru/editor/images/3/f154015f-1678-4500-b57a-6662674e0053.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D',
        '2018-05-06 10:10:01.005',
        4,
        'https://www.extremetech.com/wp-content/uploads/2018/11/WC3-Reforged-Feature-640x354.jpg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D',
        '2018-05-07 10:10:01.005',
        5,
        'https://igra-bomj.ru/wp-content/uploads/2018/11/a731091a12432210209805673515b646.jpeg');
INSERT INTO T_POST (F_TEXT, F_DATE, F_USER_ID, F_PICTURE)
VALUES ('Reforge is comming :D',
        '2018-05-08 10:10:01.005',
        6,
        'http://digitalspyuk.cdnds.net/18/44/980x490/landscape-1541191976-warcraft-3-reforged.png');

INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (1, 1);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (1, 2);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (1, 3);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (2, 1);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (2, 3);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (4, 1);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (5, 1);

INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (65, 1);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (65, 2);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (65, 3);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (64, 1);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (64, 2);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (63, 3);
INSERT INTO T_LIKE (F_POST_ID, F_USER_ID)
VALUES (63, 4);

INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 1, 'Мой первый коммент', '1998-02-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (2, 1, 'Второй!', '1999-02-22 12:10:01.007');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (2, 3, 'Я первый', '1998-02-22 12:10:01.008');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 4, 'Я первый выкусите', '1999-02-22 12:10:01.009');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 5, 'аххахах', '2000-02-22 12:10:01.010');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (3, 5, 'УХАХАХАХАХ', '2001-02-22 12:10:01.011');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 5, 'Ты чего ржешь?', '2001-02-22 12:15:01.011');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (3, 5, 'А ты чего, морда, ржешь?', '2001-02-22 12:17:01.011');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 5, 'Будешь грубить - забаню', '2001-02-22 12:19:01.011');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (3,
        5,
        'Я тогда нажалуюсь сам знаешь кому - тебе прилетит! И, да, это угроза. Так что можешь пойти уже свои вонючие портки сушить',
        '2001-02-22 12:21:01.011');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 5, 'Я тебя придупреждал. Сам виноват', '2001-02-22 12:25:01.011');

INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 65, 'Жду Варика', '2018-01-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (2, 65, 'Я тоже', '2018-02-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (3, 65, 'Я тоже', '2018-03-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (4, 65, 'На говно похоже', '2018-04-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (5, 65, 'аххахах', '2018-05-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (6, 65, 'УХАХАХАХАХ', '2018-06-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 65, 'ЛОРЕМ ИМПСУМ', '2018-07-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (2, 65, 'ЛОРЕМ ИМПСУМ', '2018-08-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 65, 'ЛОРЕМ ИМПСУМ', '2018-09-22 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 65, 'ЛОРЕМ ИМПСУМ', '2018-09-23 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (2, 65, 'ЛОРЕМ ИМПСУМ', '2018-09-24 12:10:01.006');
INSERT INTO T_COMMENT (F_USER_ID, F_POST_ID, F_TEXT, F_DATE)
VALUES (1, 65, 'ЛОРЕМ ИМПСУМ', '2018-09-25 12:10:01.006');