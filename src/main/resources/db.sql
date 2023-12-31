CREATE SCHEMA content;

CREATE TABLE content.object
(
    uuid       uuid                                   NOT NULL,
    owner_id   uuid                                   NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone               NOT NULL,
    CONSTRAINT object_pkey PRIMARY KEY (uuid)
);

CREATE TABLE content.folder
(
    object_id        uuid NOT NULL,
    name             text,
    parent_folder_id uuid,
    CONSTRAINT folder_pkey PRIMARY KEY (object_id),
    CONSTRAINT unique_folder_to_object UNIQUE (object_id),
    CONSTRAINT fk_folder_object FOREIGN KEY (object_id) REFERENCES content.object (uuid) ON DELETE CASCADE,
    CONSTRAINT fk_folder_to_folder FOREIGN KEY (parent_folder_id) REFERENCES content.folder (object_id) ON DELETE CASCADE
);


CREATE TABLE content.file
(
    object_id uuid NOT NULL,
    name      text NOT NULL,
    path      text NOT NULL,
    folder_id uuid NOT NULL,
    CONSTRAINT file_pkey PRIMARY KEY (object_id),
    CONSTRAINT unique_file_to_object UNIQUE (object_id),
    CONSTRAINT fk_file_to_folder FOREIGN KEY (folder_id) REFERENCES content.folder (object_id) ON DELETE CASCADE,
    CONSTRAINT fk_file_to_object FOREIGN KEY (object_id) REFERENCES content.object (uuid) ON DELETE CASCADE
);


ALTER TABLE content.object
    ALTER COLUMN uuid SET DEFAULT
        uuid_generate_v4();


ALTER TABLE content.file
    ADD CONSTRAINT unique_file_name_in_folder UNIQUE (name, folder_id);
ALTER TABLE content.folder
    ADD CONSTRAINT unique_folder_name_in_folder UNIQUE (name, parent_folder_id);
ALTER TABLE content.file
    ALTER COLUMN path DROP NOT NULL;

ALTER TABLE folder
    RENAME COLUMN parentFolderId TO folderid;

ALTER TABLE content.object ADD delete bool NOT NULL DEFAULT 'f';
ALTER TABLE content.object ADD write bool NOT NULL DEFAULT 'f';
ALTER TABLE content.object ADD read bool NOT NULL DEFAULT 'f';