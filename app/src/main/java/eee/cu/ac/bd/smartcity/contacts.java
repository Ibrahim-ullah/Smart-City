package eee.cu.ac.bd.smartcity;

public class contacts {

        private String Name;
        private String Phone;

        public contacts(String Name,  String Phone) {
            this.Name = Name;
            this.Phone = Phone;
        }

        /*Getters and setters to access the private members*/
        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }


        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }




}
