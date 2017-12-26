<?php
    class Login {

        private $connection;

        public function __construct(Connection $connection) {
            $this->connection = $connection;
        }

        public function tryLoginByEmail($email, $hashPass) {
            $sql = "SELECT * FROM user WHERE email=?";
            $query = $this->connection->getConnection()->prepare($sql);
            $query->execute(array($email));
            $user = $query->fetch(PDO::FETCH_ASSOC);

            if($user != null) {
                if($user['hashPass'] == $hashPass) {
                    return new User(
                        array(
                            'id' => $user['id'],
                            'email' => $user['email'],
                            'hashPass' => $user['hashPass'],
                            'customName' => $user['customName'],
                            'googleId' => $user['googleId'],
                            'points' => $user['points'],
                            'level' => $user['level']
                        ));
                } else {
                    return NULL;
                }
            }
        }
    }
?>
