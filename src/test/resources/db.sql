DROP TABLE public.administrators;

CREATE TABLE public.administrators
(
    id integer NOT NULL DEFAULT nextval('id_administrators_sec'::regclass),
    login text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT administrators_pkey PRIMARY KEY (id),
    CONSTRAINT administrators_login_key UNIQUE (login)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.administrators
    OWNER to postgres;

DROP TABLE public.commands;

CREATE TABLE public.commands
(
    id integer NOT NULL DEFAULT nextval('id_commands_sec'::regclass),
    command text COLLATE pg_catalog."default",
    format text COLLATE pg_catalog."default",
    CONSTRAINT commands_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.commands
    OWNER to postgres;

DROP TABLE public.descriptions;

CREATE TABLE public.descriptions
(
    id integer NOT NULL DEFAULT nextval('id_descriptions_sec'::regclass),
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT descriptions_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.descriptions
    OWNER to postgres;

DROP TABLE public.help;

CREATE TABLE public.help
(
    id integer NOT NULL DEFAULT nextval('id_help_sec'::regclass),
    command_id integer NOT NULL,
    description text COLLATE pg_catalog."default",
    CONSTRAINT help_pkey PRIMARY KEY (id),
    CONSTRAINT help_command_id_fkey FOREIGN KEY (command_id)
        REFERENCES public.commands (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.help
    OWNER to postgres;
DROP TABLE public.languages;

CREATE TABLE public.languages
(
    id integer NOT NULL DEFAULT nextval('id_languages_sec'::regclass),
    language text COLLATE pg_catalog."default" NOT NULL,
    short_name text COLLATE pg_catalog."default",
    CONSTRAINT languages_pkey PRIMARY KEY (id),
    CONSTRAINT languages_language_key UNIQUE (language),
    CONSTRAINT languages_short_name_key UNIQUE (short_name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.languages
    OWNER to postgres;

DROP TABLE public.translations;

CREATE TABLE public.translations
(
    id integer NOT NULL DEFAULT nextval('id_translations_sec'::regclass),
    description text COLLATE pg_catalog."default",
    language_id integer NOT NULL,
    command_id integer NOT NULL,
    CONSTRAINT translations_command_id_fkey FOREIGN KEY (command_id)
        REFERENCES public.commands (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT translations_language_id_fkey FOREIGN KEY (language_id)
        REFERENCES public.languages (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.translations
    OWNER to postgres;