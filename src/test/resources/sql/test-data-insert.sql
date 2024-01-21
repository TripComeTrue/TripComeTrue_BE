/* member */
insert into member (member_id, created_at, updated_at, authority, email, nickname, password, member_rating, profile_image, introduction)
values (1, now(), now(), 'ROLE_USER', 'member1@email.com', '치앙마이', 'password1', 4.0, 'profile image url', '치앙마이 소개글');

insert into member (member_id, created_at, updated_at, authority, email, nickname, password, member_rating, profile_image, introduction)
values (2, now(), now(), 'ROLE_USER', 'member2@email.com', '치앙 마이 러버', 'password2', 2.8, 'profile image url', '치앙 마이 러버 소개글');

insert into member (member_id, created_at, updated_at, authority, email, nickname, password, member_rating, profile_image, introduction)
values (3, now(), now(), 'ROLE_USER', 'member3@email.com', '파리지앵', 'password3', 4.3, 'profile image url', '파리지앵 소개글');

/* city */
insert into city (city_id, created_at, updated_at, country, currency, language, name, time_difference, visa, voltage,
                  weather_description, weather_recommendation, image_url, store_count)
values (1, now(), now(), 'THAILAND', 'THB', '태국어', '방콕', '2시간 느림', '90일 무비자 체류', '220V', '방콕 날씨 설명', '방콕 날씨 추천', '방콕 이미지', 45);

insert into city (city_id, created_at, updated_at, country, currency, language, name, time_difference, visa, voltage,
                  weather_description, weather_recommendation, image_url, store_count)
values (2, now(), now(), 'JAPAN', 'JPY', '일본어', '도쿄', '시차 없음', '90일 무비자 체류', '220V', '도쿄 날씨 설명', '도쿄 날씨 추천', '도쿄 이미지', 100);

insert into city (city_id, created_at, updated_at, country, currency, language, name, time_difference, visa, voltage,
                  weather_description, weather_recommendation, image_url, store_count)
values (3, now(), now(), 'USA', 'USD', '영어', '뉴욕', '14시간 느림', '90일 무비자 체류', '110V', '뉴욕 날씨 설명', '뉴욕 날씨 추천', '뉴욕 이미지', 95);

insert into city (city_id, created_at, updated_at, country, language, name, weather_description, weather_recommendation, image_url, store_count)
values (4, now(), now(), 'KOREA', '한국어', '부산', '부산 날씨 설명', '부산 날씨 추천', '부산 이미지', 88);

/* place */
insert into place (place_id, created_at, updated_at, address, city_id, description, name, stored_count, latitude, longitude, comment_count, review_count)
values (1, now(), now(), '여행지1 주소', 1, '여행지1 설명', '여행지1', 30, 130.7, 45.6, 10, 10);

insert into place (place_id, created_at, updated_at, address, city_id, description, name, stored_count, latitude, longitude, comment_count, review_count)
values (2, now(), now(), '여행지2 주소', 2, '여행지2 설명', '여행지2', 45, 130.7, 45.6, 20, 10);

insert into place (place_id, created_at, updated_at, address, city_id, description, name, stored_count, latitude, longitude, comment_count, review_count)
values (3, now(), now(), '여행지3 주소', 3, '여행지3 설명', '여행지3', 100, 130.7, 45.6, 15, 10);

insert into place (place_id, created_at, updated_at, address, city_id, description, name, stored_count, latitude, longitude, comment_count, review_count)
values (4, now(), now(), '여행지4 주소', 4, '여행지4 설명', '여행지4', 20, 130.7, 45.6, 33, 10);


/* trip_record */
insert into trip_record (trip_record_id, average_rating, content, countries, expense_range_type, title, total_days, trip_end_day, trip_start_day, view_count,
                         created_at, updated_at, member_id, comment_count, review_count, store_count)
values (1, 3.5, '여행 컨텐츠1', 'THAILAND', 'BELOW_100', '방콕 여행 제목', 4, '2024-01-25', '2024-01-22', 100, now(), now(), 1, 5, 3, 30);

insert into trip_record (trip_record_id, average_rating, content, countries, expense_range_type, title, total_days, trip_end_day, trip_start_day, view_count,
                         created_at, updated_at, member_id, comment_count, review_count, store_count)
values (2, 4.1, '여행 컨텐츠2', 'JAPAN', 'BELOW_200', '도쿄 여행 제목', 4, '2024-01-25', '2024-01-22', 100, now(), now(), 1, 5, 3, 50);

insert into trip_record (trip_record_id, average_rating, content, countries, expense_range_type, title, total_days, trip_end_day, trip_start_day, view_count,
                         created_at, updated_at, member_id, comment_count, review_count, store_count)
values (3, 2.9, '여행 컨텐츠3', 'KOREA', 'BELOW_50', '부산 여행 제목', 2, '2024-01-30', '2024-01-29', 200, now(), now(), 2, 5, 3, 45);

insert into trip_record (trip_record_id, average_rating, content, countries, expense_range_type, title, total_days, trip_end_day, trip_start_day, view_count,
                         created_at, updated_at, member_id, comment_count, review_count, store_count)
values (4, 2.9, '여행 컨텐츠4', 'USA', 'BELOW_300', '뉴욕 여행 제목', 10, '2024-02-01', '2024-02-10', 400, now(), now(), 3, 5, 3, 70);


/* trip_record_schedule */
insert into trip_record_schedule (trip_record_schedule_id, content, day_number, ordering, trip_record_id, created_at, updated_at, place_id)
values (1, '여행 스케줄1', 1, 1, 1, now(), now(), 1);

insert into trip_record_schedule (trip_record_schedule_id, content, day_number, ordering, trip_record_id, created_at, updated_at, place_id)
values (2, '여행 스케줄2', 1, 2, 1, now(), now(), 1);

insert into trip_record_schedule (trip_record_schedule_id, content, day_number, ordering, trip_record_id, created_at, updated_at, place_id)
values (3, '여행 스케줄3', 1, 1, 2, now(), now(), 2);

insert into trip_record_schedule (trip_record_schedule_id, content, day_number, ordering, trip_record_id, created_at, updated_at, place_id)
values (4, '여행 스케줄4', 1, 1, 3, now(), now(), 3);

insert into trip_record_schedule (trip_record_schedule_id, content, day_number, ordering, trip_record_id, created_at, updated_at, place_id)
values (5, '여행 스케줄5', 1, 1, 4, now(), now(), 4);

/* trip_record_schedule_video */
insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (1, now(), now(), '쇼츠1', 1, '쇼츠1 썸네일');

insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (2, now(), now(), '쇼츠2', 2, '쇼츠2 썸네일');

insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (3, now(), now(), '쇼츠3', 3, '쇼츠3 썸네일');

insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (4, now(), now(), '쇼츠4', 4, '쇼츠4 썸네일');

insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (5, now(), now(), '쇼츠5', 5, '쇼츠5 썸네일');

insert into trip_record_schedule_video (trip_record_schedule_video_id, created_at, updated_at, video_url, trip_schedule_id, thumbnail_url)
values (6, now(), now(), '쇼츠6', 1, '쇼츠6 썸네일');
