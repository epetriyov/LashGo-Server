CREATE TABLE users (
	id              serial      			  NOT NULL PRIMARY KEY,
	login           varchar(35) 			  NOT NULL UNIQUE CHECK (login <> ''),
	password        varchar(35) 			  NOT NULL CHECK (password <> ''),
	name 	        varchar(30),
	surname         varchar(30),
	about           varchar(500),
	city            varchar(30),
	birth_date      timestamp with time zone  CHECK (birth_date < current_timestamp),
	avatar	        varchar(50),
    email           varchar(50)               NOT NULL UNIQUE CHECK (email <> ''),
	is_admin        boolean                   DEFAULT FALSE	
);

CREATE TABLE temp_users (
	id              serial      			  NOT NULL PRIMARY KEY,
	login           varchar(35) 			  NOT NULL UNIQUE CHECK (login <> ''),
	password        varchar(35) 			  NOT NULL CHECK (password <> ''),
	name 	        varchar(30),
	surname         varchar(30),
	about           varchar(500),
	city            varchar(30),
	birth_date      timestamp with time zone  CHECK (birth_date < current_timestamp),
	avatar	        varchar(50),
    email           varchar(50),
	is_admin        boolean                   DEFAULT FALSE	
);

CREATE TABLE client_interfaces (
	id              serial      			  NOT NULL PRIMARY KEY,
	code            varchar(30)	              NOT NULL UNIQUE CHECK (code <> ''),
	name            varchar(50)	              NOT NULL UNIQUE CHECK (name <> '')	
);

CREATE TABLE users_interfaces (
	id              serial      			  NOT NULL PRIMARY KEY,
    user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,	
    interface_id    int                       REFERENCES client_interfaces (id) ON DELETE RESTRICT ON UPDATE CASCADE	    
);

CREATE UNIQUE INDEX users_interfaces_idx ON users_interfaces (user_id, interface_id);
	
CREATE TABLE sessions (
	session_id      varchar(35)               NOT NULL PRIMARY KEY CHECK (session_id <>''),
    user_id         int                       UNIQUE REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    start_time      timestamp with time zone  NOT NULL DEFAULT current_timestamp
);	

CREATE TABLE gcm_registrations (
	registration_id varchar(200)              NOT NULL PRIMARY KEY CHECK (registration_id <> ''),
    user_id         int                       UNIQUE REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);    

CREATE TABLE checks (
	id              serial                    NOT NULL PRIMARY KEY,
	name            varchar(50)               NOT NULL UNIQUE CHECK (name <> ''),
	description     varchar(500)              NOT NULL CHECK (description <> ''),
	start_date      timestamp with time zone  NOT NULL CHECK (start_date >= current_timestamp) DEFAULT current_timestamp,
	duration        int 		              NOT NULL CHECK (duration > 0 AND duration < 12) DEFAULT 3,
	photo           varchar(50),	
	vote_duration   int 		              NOT NULL CHECK (vote_duration > 0 AND vote_duration < 12) DEFAULT 3,
);

CREATE TABLE photos (
	id              serial                    NOT NULL PRIMARY KEY,
	picture	        varchar(50)               NOT NULL UNIQUE CHECK (picture <> ''),
	make_date       timestamp with time zone  NOT NULL CHECK (make_date <= current_timestamp) DEFAULT current_timestamp,
	rating          int                       DEFAULT 0 CHECK (rating > 0),
    user_id         int                       REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,	
    check_id        int                       REFERENCES checks (id) ON DELETE RESTRICT ON UPDATE CASCADE    
);

CREATE UNIQUE INDEX photo_idx ON photos (user_id, check_id);

CREATE TABLE user_ratings (
	id              serial                    NOT NULL PRIMARY KEY,
	photo_id        int                       REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE,	
	user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE UNIQUE INDEX user_rating_idx ON user_ratings (user_id, photo_id);

CREATE TABLE comments (
	id 				serial                    NOT NULL PRIMARY KEY,
	content			varchar(500)              NOT NULL,
	create_date     timestamp with time zone  NOT NULL CHECK (create_date <= current_timestamp) DEFAULT current_timestamp,
    user_id         int                       REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE	
);

CREATE TABLE photo_comments (
    id              serial                    NOT NULL PRIMARY KEY,
    comment_id      int                       REFERENCES comments (id) ON DELETE CASCADE ON UPDATE CASCADE,
    photo_id        int                       REFERENCES photos (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX photo_comments_idx ON photo_comments (photo_id, comment_id);

CREATE TABLE check_comments (
    id              serial                    NOT NULL PRIMARY KEY,
    comment_id      int                       REFERENCES comments (id) ON DELETE CASCADE ON UPDATE CASCADE,
    check_id        int                       REFERENCES checks (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX check_comments_idx ON check_comments (check_id, comment_id);

CREATE TABLE subscriptions (
    id              serial                    NOT NULL PRIMARY KEY,
    user_id         int                       REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,	    
    checklist_id    int                       CHECK (checklist_id <> user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE	    
);

CREATE UNIQUE INDEX subscriptions_idx ON subscriptions (user_id, checklist_id);

CREATE TABLE content (
    id              serial                    NOT NULL PRIMARY KEY,
	create_date     timestamp with time zone  NOT NULL CHECK (create_date <= current_timestamp) DEFAULT current_timestamp,    
	value			varchar(500)              NOT NULL CHECK (value <> '')
);