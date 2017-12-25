<?php
    require_once("config.php");

    class Connection {
        private static $host;
        private static $dbname;
        private static $username;
        private static $password;
        private static $connection;

        public function __construct()
        {
            self::$host = $CONFIG['host'];
            self::$dbname = $CONFIG['dbname'];
            self::$username = $CONFIG['username'];
            self::$password = $CONFIG['password'];
        }

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
