<?php
    class Login {
        private $connection;

        public function __construct(Connection $connection) {
            $this->connection = $connection;
        }

        public function tryLoginByEmail($email, $hashPass) {
            $select_query = "SELECT email, password FROM user WHERE email=\"".$email
            ."\" AND password=".$hashPass;

            $res = $connection->getConnection().query($select_query);

            return $res->rowCount() == 1;
        }
    }
?>
