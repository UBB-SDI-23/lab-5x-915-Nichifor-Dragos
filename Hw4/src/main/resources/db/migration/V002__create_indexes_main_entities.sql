CREATE INDEX c_brand_index ON cars(brand);
CREATE INDEX c_cylindrical_capacity_index ON cars(cylindrical_capacity);
CREATE INDEX c_description_index ON cars(description);
CREATE INDEX c_gear_box_index ON cars(gear_box);
CREATE INDEX c_horse_power_index ON cars(horse_power);
CREATE INDEX c_motorization_index ON cars(motorization);
CREATE INDEX c_pilot_index ON cars(pilot_id);
CREATE INDEX c_user_index ON cars(user_id);


CREATE INDEX p_date_index ON pilots(date);
CREATE INDEX p_driving_experience_index ON pilots(driving_experience);
CREATE INDEX p_first_name_index ON pilots(first_name);
CREATE INDEX p_last_name_index ON pilots(last_name);
CREATE INDEX p_nationality_index ON pilots(nationality);
CREATE INDEX p_user_index ON pilots(user_id);


CREATE INDEX r_country_index ON races(country);
CREATE INDEX r_date_index ON races(date);
CREATE INDEX r_lap_length_index ON races(lap_length);
CREATE INDEX r_number_of_laps_index ON races(number_of_laps);
CREATE INDEX r_name_index ON races(name);
CREATE INDEX r_user_index ON races(user_id);


CREATE INDEX rp_pilot ON races_pilots(pilot_id);
CREATE INDEX rp_race ON races_pilots(race_id);
CREATE INDEX rp_need_accommodation ON races_pilots(need_accommodation);
CREATE INDEX rp_start_position ON races_pilots(start_position);
CREATE INDEX rp_user_index ON races_pilots(user_id);

