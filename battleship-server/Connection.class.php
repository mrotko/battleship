<?php
    include_once("config.php");

    class Connection {
        private static $host = $CONFIG['host'];
        private static $dbname = $CONFIG['dbname'];
        private static $username = $CONFIG['username'];
        private static $password = $CONFIG['password'];
        private static $connection;

        public function getConnection() {
            if(is_null(self::$connection)) {
                try {
                    self::$connection = new PDO("mysql:host=".self::$host.";dbname=".self::$dbname, self::$username, self::$password);
                    self::$connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                    echo "Success";
                } catch(PDOException $e) {
                    echo $e;
                    die("Database connection error");
                }
            }

            return self::$connection;
        }

        public function destroyConnection() {
            self::$connection = NULL;
        }
    }
?>
