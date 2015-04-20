CREATE TABLE users (
	id              serial      			        NOT NULL PRIMARY KEY,
	login           varchar(35) 			        NOT NULL UNIQUE CHECK (login <> ''),
	password        varchar(250) 			        NOT NULL CHECK (password <> ''),
	fio   	        varchar(100),
	about           varchar(500),
	city            varchar(30),
	birth_date      timestamp with time zone  CHECK (birth_date < clock_timestamp()),
	avatar	        varchar(150),
  email           varchar(50)               UNIQUE,
	is_admin        boolean                   DEFAULT FALSE	
);

CREATE TABLE socials (
  login           varchar(35) 			        NOT NULL UNIQUE CHECK (login <> ''),
  access_token    varchar(250)			        NOT NULL UNIQUE CHECK (access_token <> '')
);

CREATE TABLE social_users (
  id              serial      			        NOT NULL PRIMARY KEY,
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  social_login    varchar(35)               UNIQUE REFERENCES socials (login) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE client_interfaces (
	id              serial      			        NOT NULL PRIMARY KEY,
	code            varchar(30)	              NOT NULL UNIQUE CHECK (code <> ''),
	name            varchar(50)	              NOT NULL UNIQUE CHECK (name <> '')	
);

CREATE TABLE users_interfaces (
	id              serial     			          NOT NULL PRIMARY KEY,
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  interface_id    int                       REFERENCES client_interfaces (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE UNIQUE INDEX users_interfaces_idx ON users_interfaces (user_id, interface_id);
	
CREATE TABLE sessions (
	session_id      varchar(35)               NOT NULL PRIMARY KEY CHECK (session_id <>''),
  user_id         int                       UNIQUE REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  start_time      timestamp with time zone  NOT NULL DEFAULT clock_timestamp()
);

CREATE TABLE gcm_registrations (
	id              serial            			  NOT NULL PRIMARY KEY,
	registration_id varchar(200)              NOT NULL UNIQUE CHECK (registration_id <> ''),
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE checks (
	id              serial                    NOT NULL PRIMARY KEY,
	name            varchar(50)               NOT NULL CHECK (name <> ''),
	description     varchar(500)              NOT NULL CHECK (description <> ''),
	start_date      timestamp with time zone  NOT NULL DEFAULT clock_timestamp(),
	duration        int     		              NOT NULL CHECK (duration > 0 AND duration < 12) DEFAULT 3,
	task_photo      varchar(50),	
	vote_duration   int 		                  NOT NULL CHECK (vote_duration > 0 AND vote_duration < 12) DEFAULT 3,
  check_type      varchar(50)               DEFAULT 'SELFIE' REFERENCES check_type (code) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE check_winners (
  id              serial                    NOT NULL PRIMARY KEY,
  check_id        int                       UNIQUE REFERENCES checks (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  winner_id       int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE photos (
	id              bigserial                 NOT NULL PRIMARY KEY,
	picture	        varchar(50)               NOT NULL UNIQUE CHECK (picture <> ''),
	make_date       timestamp with time zone  NOT NULL CHECK (make_date <= clock_timestamp()) DEFAULT clock_timestamp(),
  user_id         int                       REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  check_id        int                       REFERENCES checks (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  is_banned       int                       DEFAULT 0
);

CREATE UNIQUE INDEX photo_idx ON photos (user_id, check_id);

CREATE TABLE comments (
	id 				      bigserial                 NOT NULL PRIMARY KEY,
	content			    varchar(500)              NOT NULL,
	create_date     timestamp with time zone  NOT NULL CHECK (create_date <= clock_timestamp()) DEFAULT clock_timestamp(),
  user_id         int                       REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE photo_comments (
  id              bigserial                 NOT NULL PRIMARY KEY,
  comment_id      bigint                    REFERENCES comments (id) ON DELETE CASCADE ON UPDATE CASCADE,
  photo_id        int                       REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX photo_comments_idx ON photo_comments (photo_id, comment_id);

CREATE TABLE check_comments (
  id              bigserial                 NOT NULL PRIMARY KEY,
  comment_id      bigint                    REFERENCES comments (id) ON DELETE CASCADE ON UPDATE CASCADE,
  check_id        int                       REFERENCES checks (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX check_comments_idx ON check_comments (check_id, comment_id);

CREATE TABLE subscriptions (
  id              bigserial                 NOT NULL PRIMARY KEY,
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  checklist_id    int                       CHECK (checklist_id <> user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  subscribe_date  timestamp with time zone  NOT NULL CHECK (subscribe_date <= clock_timestamp()) DEFAULT clock_timestamp()
);

CREATE UNIQUE INDEX subscriptions_idx ON subscriptions (user_id, checklist_id);

CREATE TABLE news (
	id 				      serial				            NOT NULL PRIMARY KEY,
	theme           varchar(150)              NOT NULL UNIQUE CHECK (theme <> ''),
	content         varchar(500)              NOT NULL CHECK (content <> ''),	
	create_date     timestamp with time zone  NOT NULL CHECK (create_date <= clock_timestamp()) DEFAULT clock_timestamp(),
	image_url       varchar(50)
);

CREATE TABLE user_votes (
	id      				bigserial			        		NOT NULL PRIMARY KEY,
	user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,	    
  photo_id        bigint                    REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_votes_idx ON user_votes (user_id, photo_id);

CREATE TABLE user_shown_photos (
	id 				      bigserial					        NOT NULL PRIMARY KEY,
	user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  photo_id        bigint                    REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_shown_photos_idx ON user_shown_photos (user_id, photo_id);

CREATE TABLE user_check_likes (
	id              bigserial                 NOT NULL PRIMARY KEY,
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  check_id        int                       REFERENCES checks (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_check_likes_idx ON user_check_likes (check_id, user_id);

CREATE TABLE user_photo_likes (
  id              bigserial                 NOT NULL PRIMARY KEY,
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  photo_id        bigint                    REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_photo_likes_idx ON user_photo_likes (photo_id, user_id);

CREATE TABLE events (
  id             bigserial                  NOT NULL PRIMARY KEY,
  user_id        int                        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  photo_id       bigint                     REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE,
  check_id       int                        REFERENCES checks (id) ON DELETE CASCADE ON UPDATE CASCADE,
  action         varchar(50)                NOT NULL CHECK (action <> ''),
  event_date     timestamp with time zone   NOT NULL CHECK (event_date <= clock_timestamp()) DEFAULT clock_timestamp(),
  object_user_id int                        REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE user_complains (
  id             bigserial                  NOT NULL PRIMARY KEY,
  user_id        int                        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  complain_date  timestamp with time zone   NOT NULL CHECK (complain_date <= clock_timestamp()) DEFAULT clock_timestamp(),
  photo_id       bigint                     REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_complains_idx ON user_complains (photo_id, user_id);

CREATE TABLE apns_registrations (
  id              serial            		    NOT NULL PRIMARY KEY,
  register_date   timestamp with time zone  NOT NULL CHECK (register_date <= clock_timestamp()) DEFAULT clock_timestamp(),
  token           varchar(200)              NOT NULL UNIQUE CHECK (token <> ''),
  user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE check_type (
  code            varchar(50)               NOT NULL PRIMARY KEY,
  name            varchar(150)              NOT NULL CHECK (name <> '')
);

alter table checks add column   check_type      varchar(50)  DEFAULT 'SELFIE'            REFERENCES check_type (code) ON DELETE RESTRICT ON UPDATE CASCADE;