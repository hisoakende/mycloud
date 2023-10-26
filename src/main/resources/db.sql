--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0 (Debian 14.0-1.pgdg110+1)
-- Dumped by pg_dump version 15.2 (Homebrew petere/postgresql)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: content; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA content;


ALTER SCHEMA content OWNER TO postgres;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- Name: gettimestampplpgsql(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.gettimestampplpgsql() RETURNS timestamp without time zone
    LANGUAGE plpgsql
    AS $$ BEGIN
RETURN CURRENT_TIMESTAMP;
END;
$$;


ALTER FUNCTION public.gettimestampplpgsql() OWNER TO postgres;

--
-- Name: set_updated_time(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.set_updated_time() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
NEW.updated_at = NOW();
RETURN NEW;
END;
$$;


ALTER FUNCTION public.set_updated_time() OWNER TO postgres;

--
-- Name: update_updated_at(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_updated_at() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
NEW.updated_at = NOW();
RETURN NEW;
END
;
$$;


ALTER FUNCTION public.update_updated_at() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: file; Type: TABLE; Schema: content; Owner: postgres
--

CREATE TABLE content.file (
                              object_id uuid NOT NULL,
                              name text NOT NULL,
                              path text NOT NULL,
                              folder_id uuid NOT NULL
);


ALTER TABLE content.file OWNER TO postgres;

--
-- Name: folder; Type: TABLE; Schema: content; Owner: postgres
--

CREATE TABLE content.folder (
                                object_id uuid NOT NULL,
                                name text,
                                parent_folder_id uuid
);


ALTER TABLE content.folder OWNER TO postgres;

--
-- Name: object; Type: TABLE; Schema: content; Owner: postgres
--

CREATE TABLE content.object (
                                uuid uuid NOT NULL,
                                created_at timestamp with time zone DEFAULT now() NOT NULL,
                                updated_at timestamp with time zone NOT NULL
);


ALTER TABLE content.object OWNER TO postgres;

--
-- Data for Name: file; Type: TABLE DATA; Schema: content; Owner: postgres
--

COPY content.file (object_id, name, path, folder_id) FROM stdin;
\.


--
-- Data for Name: folder; Type: TABLE DATA; Schema: content; Owner: postgres
--

COPY content.folder (object_id, name, parent_folder_id) FROM stdin;
\.


--
-- Data for Name: object; Type: TABLE DATA; Schema: content; Owner: postgres
--

COPY content.object (uuid, created_at, updated_at) FROM stdin;
\.


--
-- Name: file file_pkey; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (object_id);


--
-- Name: folder folder_pkey; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.folder
    ADD CONSTRAINT folder_pkey PRIMARY KEY (object_id);


--
-- Name: object object_pkey; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.object
    ADD CONSTRAINT object_pkey PRIMARY KEY (uuid);


--
-- Name: file unique_file_to_object; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.file
    ADD CONSTRAINT unique_file_to_object UNIQUE (object_id);


--
-- Name: folder unique_folder_to_object; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.folder
    ADD CONSTRAINT unique_folder_to_object UNIQUE (object_id);


--
-- Name: object trigger_update_updated_at; Type: TRIGGER; Schema: content; Owner: postgres
--

CREATE TRIGGER trigger_update_updated_at BEFORE INSERT OR UPDATE ON content.object FOR EACH ROW EXECUTE FUNCTION public.update_updated_at();


--
-- Name: file fk_file_to_folder; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.file
    ADD CONSTRAINT fk_file_to_folder FOREIGN KEY (folder_id) REFERENCES content.folder(object_id);


--
-- Name: file fk_file_to_object; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.file
    ADD CONSTRAINT fk_file_to_object FOREIGN KEY (object_id) REFERENCES content.object(uuid);


--
-- Name: folder fk_folder_object; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.folder
    ADD CONSTRAINT fk_folder_object FOREIGN KEY (object_id) REFERENCES content.object(uuid);


--
-- Name: folder fk_folder_to_folder; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content.folder
    ADD CONSTRAINT fk_folder_to_folder FOREIGN KEY (parent_folder_id) REFERENCES content.folder(object_id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

