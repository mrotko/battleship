<?php
    require_once("User.class.php");

    class Register {
        private $connection;

        public function __construct(Connection $connection) {
            $this->connection = $connection;
        }

        public function register(User $user) {
            if(!$this->checkIfUserExists($user->getEmail())) {
                $sql = "INSERT INTO user (email, hashPass, customName, googleId, points, level)
                                    VALUES (?, ?, ?, ?, ?, ?)";

                $this->connection->getConnection()->prepare($sql)->execute(
                    array(  $user->getEmail(),
                            $user->getHashPass(),
                            $user->getCustomName(),
                            $user->getGoogleId(),
                            $user->getPoints(),
                            $user->getLevel())
                );

                return $this->checkIfUserExists($user->getEmail());
            } else {
                return false;
            }
        }

        private function checkIfUserExists($email) {
            $sql = "SELECT email FROM user WHERE email=\"".$email."\"";
            $select_email_response = $this->connection->getConnection()->query($sql);
            return $select_email_response->rowCount() != 0;
        }
    }
?>
