use `loadbalancing`;

ALTER TABLE `lb_usage` ADD `reference_id` VARCHAR(256);

update `meta` set `meta_value` = '61' where `meta_key`='version';