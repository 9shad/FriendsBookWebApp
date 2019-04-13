CREATE TABLE `useraccount` (
 `user_id` varchar(10) NOT NULL,
 `password` varchar(255) NOT NULL,
 `name` varchar(50) NOT NULL,
 `gender` varchar(10) NOT NULL,
 `school_name` varchar(100) NOT NULL,
 `birthday` date NOT NULL,
 `email` varchar(100) NOT NULL,
 `account_created_on` varchar(100) NOT NULL,
 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user_notification` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `notification_type` varchar(20) NOT NULL,
 `status` varchar(20) NOT NULL,
 `user_id` varchar(10) NOT NULL,
 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `friend_request` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `notification_id` int(11) NOT NULL,
 `from_userid` varchar(10) NOT NULL,
 `to_userid` varchar(10) NOT NULL,
 `status` varchar(15) NOT NULL,
 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user_message` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `from_userid` varchar(10) NOT NULL,
 `to_userid` varchar(10) NOT NULL,
 `description` varchar(500) NOT NULL,
 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 `notification_id` int(11) NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user_post` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `post_type` varchar(10) NOT NULL,
 `user_id` varchar(10) NOT NULL,
 `description` varchar(500) NOT NULL,
 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user_hashtag` (
 `hashtag` varchar(100) NOT NULL,
 `post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user_comment` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `postId` int(11) NOT NULL,
 `from_UserId` varchar(12) NOT NULL,
 `description` varchar(255) NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8