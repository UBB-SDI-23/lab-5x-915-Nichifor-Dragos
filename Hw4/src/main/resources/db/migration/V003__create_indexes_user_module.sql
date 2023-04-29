CREATE INDEX role_name_index ON roles(name);


CREATE INDEX ujwt_jwt_token_index ON user_jwts(jwt_token);
CREATE INDEX ujwt_username_index ON user_jwts(username);
CREATE INDEX ujwt_password_index ON user_jwts(password);


CREATE INDEX up_bio_index ON user_profiles(bio);
CREATE INDEX up_birthdate_index ON user_profiles(birthdate);
CREATE INDEX up_gender_index ON user_profiles(gender);
CREATE INDEX up_location_index ON user_profiles(location);
CREATE INDEX up_marital_status_index ON user_profiles(marital_status);


CREATE INDEX ur_role_id_index ON user_roles(role_id);
CREATE INDEX ur_user_id_index ON user_roles(user_id);


CREATE INDEX u_password_index ON users(password);
CREATE INDEX u_username_index ON users(username);
CREATE INDEX u_user_profile_id_index ON users(user_profile_id);