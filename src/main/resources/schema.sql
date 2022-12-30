drop table if exists irrigation_log, slot, plot;

CREATE TABLE plot
(
    id                      BIGINT auto_increment,
    agricultural_crop       VARCHAR(255),
    cultivated_area         BIGINT,
    wait_before_close_slots INT NOT NULL,
    amount_water            FLOAT,
    irrigation_every        INT NOT NULL,
    retry_before_alert      INT NOT NULL default 3,
    deleted                 BIT NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
);

CREATE TABLE slot
(
      id      BIGINT auto_increment,
      plot_id BIGINT NOT NULL,
      deleted BIT NOT NULL DEFAULT 0,
      PRIMARY KEY(id),
      FOREIGN KEY(plot_id) REFERENCES plot(id)
);

CREATE TABLE irrigation_log
(
        id        BIGINT auto_increment,
        date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        details   VARCHAR(255),
        status    TINYINT NOT NULL,
        slot_id   BIGINT NOT NULL,
        deleted   BIT NOT NULL DEFAULT 0,
        PRIMARY KEY(id),
        FOREIGN KEY(slot_id) REFERENCES slot(id)
);