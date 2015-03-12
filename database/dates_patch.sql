ALTER TABLE apns_registrations DROP CONSTRAINT IF EXISTS apns_registrations_register_date_check;
ALTER TABLE apns_registrations ADD CONSTRAINT apns_date_check CHECK (register_date <= clock_timestamp());
ALTER TABLE comments DROP CONSTRAINT IF EXISTS comments_create_date_check;
ALTER TABLE comments ADD CONSTRAINT comments_date_check CHECK (create_date <= clock_timestamp());
ALTER TABLE events DROP CONSTRAINT IF EXISTS events_event_date_check;
ALTER TABLE events ADD CONSTRAINT events_date_check CHECK (event_date <= clock_timestamp());
ALTER TABLE news DROP CONSTRAINT IF EXISTS news_create_date_check;
ALTER TABLE news ADD CONSTRAINT news_date_check CHECK (create_date <= clock_timestamp());
ALTER TABLE photos DROP CONSTRAINT IF EXISTS photos_make_date_check;
ALTER TABLE photos ADD CONSTRAINT photos_date_check CHECK (make_date <= clock_timestamp());
ALTER TABLE subscriptions DROP CONSTRAINT IF EXISTS subscriptions_subscribe_date_check;
ALTER TABLE subscriptions ADD CONSTRAINT subscriptions_date_check CHECK (subscribe_date <= clock_timestamp());
ALTER TABLE user_complains DROP CONSTRAINT IF EXISTS user_complains_complain_date_check;
ALTER TABLE user_complains ADD CONSTRAINT complains_date_check CHECK (complain_date <= clock_timestamp());
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_birth_date_check;
ALTER TABLE users ADD CONSTRAINT users_date_check CHECK (birth_date < clock_timestamp());
ALTER TABLE checks DROP CONSTRAINT checks_duration_check;
ALTER TABLE checks ADD CONSTRAINT checks_duration_check CHECK (duration > 0);
ALTER TABLE checks DROP CONSTRAINT checks_vote_duration_check;
ALTER TABLE checks ADD CONSTRAINT checks_vote_duration_check CHECK (vote_duration > 0);



