DO
$$DECLARE
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'users_group')
	THEN
        CREATE ROLE users_group
            NOSUPERUSER NOCREATEROLE NOCREATEDB NOREPLICATION;
                CREATE ROLE user_role LOGIN
                  ENCRYPTED PASSWORD 'secretPassword'
                  NOSUPERUSER NOCREATEROLE NOCREATEDB NOREPLICATION;
                GRANT users_group TO user_role;
    END IF;
END
$$