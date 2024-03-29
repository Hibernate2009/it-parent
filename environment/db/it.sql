-- Source table: DEL_DELIVERY
CREATE TABLE DEL_DELIVERY (
  ID VARCHAR(255), 
  CONTEXT TEXT, 
  QUEUE VARCHAR(255), 
  STATUS VARCHAR(255), 
  DATE_EVENT TIMESTAMP(3) WITHOUT TIME ZONE, 
  type VARCHAR(255)
) WITH OIDS;

-- Source index: DEL_DELIVERY_ID_idx
CREATE INDEX DEL_DELIVERY_ID_idx ON DEL_DELIVERY
(ID);

-- Source index: DEL_DELIVERY_STATUS_idx
CREATE INDEX DEL_DELIVERY_STATUS_idx ON DEL_DELIVERY
(STATUS);