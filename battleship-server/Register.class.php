<?php
    require_once("User.class.php");

    class Register {
        private $connection;

        public function __construct(Connection $connection) {
            $this->connection = $connection;
        }

        public function register(User $user) {

            if(!checkIfUserExists($user->getEmail())) {
                $insert_sql = "INSERT INTO user (email, password, customName, googleId, points, level)
                                    VALUES (?, ?, ?, ?, ?, ?)";

                $connection->getConnection()->prepare($insert_sql)->execute(
                    array(  $user->getEmail(),
                            $user->getHashPass(),
                            $user->getCustomName(),
                            $user->getGoogleId(),
                            $user->getPoints(),
                            $user->getLevel())
                );
            } else {
                return false;
            }
        }

        private function checkIfUserExists($email) {
            $query_select_email = "SELECT email FROM user WHERE email=".$email;
            $select_email_response = $connection->getConnection().query($query_select_email);
            return $select_email_response->rowCount() == 0;
        }
    }
?>
