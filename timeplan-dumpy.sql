--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.2

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
-- Name: bulan; Type: TYPE; Schema: public; Owner: timeplan_owner
--

CREATE TYPE public.bulan AS ENUM (
    'JANUARI',
    'FEBRUARI',
    'MARET',
    'APRIL',
    'MEI',
    'JUNI',
    'JULI',
    'AGUSTUS',
    'SEPTEMBER',
    'OKTOBER',
    'NOVEMBER',
    'DESEMBER'
);


ALTER TYPE public.bulan OWNER TO timeplan_owner;

--
-- Name: jenis; Type: TYPE; Schema: public; Owner: timeplan_owner
--

CREATE TYPE public.jenis AS ENUM (
    'PROYEK',
    'NON_PROYEK',
    'PROYEK_KOLABORASI',
    'NON_PROYEK_KOLABORASI'
);


ALTER TYPE public.jenis OWNER TO timeplan_owner;

--
-- Name: status; Type: TYPE; Schema: public; Owner: timeplan_owner
--

CREATE TYPE public.status AS ENUM (
    'START',
    'FINISH'
);


ALTER TYPE public.status OWNER TO timeplan_owner;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bidang_ime; Type: TABLE; Schema: public; Owner: timeplan_owner
--

CREATE TABLE public.bidang_ime (
    nama_bidang text NOT NULL,
    password_bidang text,
    nama_ketua_bidang text,
    nama_pengurus_bidang text[]
);


ALTER TABLE public.bidang_ime OWNER TO timeplan_owner;

--
-- Name: milestone_proker_ime; Type: TABLE; Schema: public; Owner: timeplan_owner
--

CREATE TABLE public.milestone_proker_ime (
    nama_proker text,
    nama_milestone text,
    progres_milestone public.status,
    deskripsi_milestone text
);


ALTER TABLE public.milestone_proker_ime OWNER TO timeplan_owner;

--
-- Name: proker_ime; Type: TABLE; Schema: public; Owner: timeplan_owner
--

CREATE TABLE public.proker_ime (
    nama_bidang text,
    nama_proker text NOT NULL,
    steering_comittee text,
    jenis_proker public.jenis
);


ALTER TABLE public.proker_ime OWNER TO timeplan_owner;

--
-- Name: proker_per_bulan; Type: TABLE; Schema: public; Owner: timeplan_owner
--

CREATE TABLE public.proker_per_bulan (
    nama_bidang text,
    nama_proker text NOT NULL,
    tanggal_start integer,
    bulan_start public.bulan,
    tanggal_end integer,
    bulan_end public.bulan
);


ALTER TABLE public.proker_per_bulan OWNER TO timeplan_owner;

--
-- Data for Name: bidang_ime; Type: TABLE DATA; Schema: public; Owner: timeplan_owner
--

COPY public.bidang_ime (nama_bidang, password_bidang, nama_ketua_bidang, nama_pengurus_bidang) FROM stdin;
PIPTEK	chamber	Nahl Syareza	{"Rakha Raditya",Shofiyah,Damien,Ilham}
KOMINFO	psbzoub	Attafahqi	{Hasmir,Fadel,Ge,Kania,Meydi}
SIWA	menangtcjuara1	Aksal	{Dimas,Harvan,Apap,Bahop,Cipeng}
\.


--
-- Data for Name: milestone_proker_ime; Type: TABLE DATA; Schema: public; Owner: timeplan_owner
--

COPY public.milestone_proker_ime (nama_proker, nama_milestone, progres_milestone, deskripsi_milestone) FROM stdin;
TECHNOSKILL	Rakoor 1.0	START	Dilaksanakan Sabtu tgl 15 Juni
TECHNOSKILL	Pembagian Jobdesc BP	START	Dilaksanakan Minggu 16 Juni
Grand Opening IME	D-DAY	FINISH	Semua siap tinggal dilaksanakan
Gladiator	Mencari CaPO	FINISH	Udah dapet dua\nAkew\nPadli
\.


--
-- Data for Name: proker_ime; Type: TABLE DATA; Schema: public; Owner: timeplan_owner
--

COPY public.proker_ime (nama_bidang, nama_proker, steering_comittee, jenis_proker) FROM stdin;
PIPTEK	TECHNOSKILL	Nahl Syareza	NON_PROYEK
PIPTEK	ROTOM 1.0	Shofiyah	NON_PROYEK
KOMINFO	Grand Opening IME	Attafahqi	NON_PROYEK
SIWA	Gladiator	Aksal	PROYEK
\.


--
-- Data for Name: proker_per_bulan; Type: TABLE DATA; Schema: public; Owner: timeplan_owner
--

COPY public.proker_per_bulan (nama_bidang, nama_proker, tanggal_start, bulan_start, tanggal_end, bulan_end) FROM stdin;
PIPTEK	TECHNOSKILL	13	JULI	16	JULI
PIPTEK	ROTOM 1.0	8	MARET	22	MEI
KOMINFO	Grand Opening IME	1	MARET	1	MARET
SIWA	Gladiator	31	MARET	28	JUNI
\.


--
-- Name: bidang_ime bidang_ime_pkey; Type: CONSTRAINT; Schema: public; Owner: timeplan_owner
--

ALTER TABLE ONLY public.bidang_ime
    ADD CONSTRAINT bidang_ime_pkey PRIMARY KEY (nama_bidang);


--
-- Name: proker_ime proker_ime_pkey; Type: CONSTRAINT; Schema: public; Owner: timeplan_owner
--

ALTER TABLE ONLY public.proker_ime
    ADD CONSTRAINT proker_ime_pkey PRIMARY KEY (nama_proker);


--
-- Name: proker_per_bulan proker_per_bulan_pkey; Type: CONSTRAINT; Schema: public; Owner: timeplan_owner
--

ALTER TABLE ONLY public.proker_per_bulan
    ADD CONSTRAINT proker_per_bulan_pkey PRIMARY KEY (nama_proker);


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: cloud_admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE cloud_admin IN SCHEMA public GRANT ALL ON SEQUENCES TO neon_superuser WITH GRANT OPTION;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: cloud_admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE cloud_admin IN SCHEMA public GRANT ALL ON TABLES TO neon_superuser WITH GRANT OPTION;


--
-- PostgreSQL database dump complete
--

