<?php
    class User {
        private $id;
        private $email;
        private $hashPass;
        private $customName;
        private $googleId;
        private $points;
        private $level;

        public function __construct($array_data) {
            $this->id = $array_data['id'];
            $this->email = $array_data['email'];
            $this->hashPass = $array_data['hashPass'];
            $this->customName = $array_data['customName'];
            $this->googleId = $array_data['googleId'];
            $this->points = $array_data['points'];
            $this->level = $array_data['level'];
        }

        public function getId() {
            return $this->id;
        }

        public function setId($id) {
            $this->id = $id;
        }

        public function getEmail() {
            return $this->email;
        }

        public function setEmail($email) {
            $this->email = $email;
        }

        public function setHashPass($hashPass) {
            $this->hashPass = $hashPass;
        }

        public function getHashPass() {
            return $this->hashPass;
        }

        public function getCustomName() {
            return $this->customName;
        }

        public function setCustomName($customName) {
            $this->customName = $customName;
        }

        public function getGoogleId() {
            return $this->googleId;
        }

        public function setGoogleId($googleId) {
            $this->googleId = $googleId;
        }

        public function getPoints() {
            return $this->points;
        }

        public function setPoints($points) {
            $this->points = $points;
        }

        public function getLevel() {
            return $this->level;
        }

        public function setLevel($level) {
            $this->level = $level;
        }

        public function getArray() {
            return array(
                'id' => $this->id,
                'email' => $this->email,
                'hashPass' => $this->hashPass,
                'customName' => $this->customName,
                'googleId' => $this->googleId,
                'points' => $this->points,
                'level' => $this->level
            );
        }
    }
