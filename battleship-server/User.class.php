<?php
    class User
    {
        private $email;
        private $hashPass;
        private $customName;
        private $googleId;
        private $points;
        private $level;
        
        public function __construct($array_data)
        {
            $email = $array_data['email'];
            $hashPass = $array_data['hashPass'];
            $customName = $array_data['customName'];
            $googleId = $array_data['googleId'];
            $points = $array_data['points'];
            $level = $array_data['level'];
        }

        public function getEmail()
        {
            return $email;
        }

        public function setEmail($email)
        {
            $this->email = $email;
        }

        public function setHashPass($hashPass)
        {
            $this->hashPass = $hashPass;
        }

        public function getHashPass()
        {
            return $hashPass;
        }

        public function getCustomName()
        {
            return $customName;
        }

        public function setCustomName($customName)
        {
            $this->customName = $customName;
        }

        public function getGoogleId()
        {
            return $googleId;
        }

        public function setGoogleId($googleId)
        {
            $this->googleId = $googleId;
        }

        public function getPoints()
        {
            return $poins;
        }

        public function setPoints($points)
        {
            $this->points = $points;
        }

        public function getLevel()
        {
            return $level;
        }

        public function setLevel($level)
        {
            $this->level = $level;
        }
    }
