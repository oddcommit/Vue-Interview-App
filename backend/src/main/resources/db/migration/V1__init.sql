create table if not exists public.user_data (
	ID bigserial PRIMARY KEY,
	email varchar(255) NOT NULL UNIQUE,
	name varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	gender varchar(255) NOT NULL,
	age integer NOT NULL,
	creation_date timestamp NOT NULL,
	modification_date timestamp NOT NULL
);

create table if not exists public.user_role (
	ID bigserial PRIMARY KEY,
	role_name varchar(255) NOT NULL,
	user_id bigint NOT NULL REFERENCES user_data(ID),
	creation_date timestamp NOT NULL,
	modification_date timestamp NOT NULL
);

commit;