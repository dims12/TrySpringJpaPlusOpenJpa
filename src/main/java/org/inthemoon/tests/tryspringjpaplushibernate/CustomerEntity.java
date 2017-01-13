package org.inthemoon.tests.tryspringjpaplushibernate;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER", schema = "PUBLIC")
public class CustomerEntity {

   private int id;
   private String nam;

   @Id
   @Column(name = "ID")
   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @Basic
   @Column(name = "NAM")
   public String getNam() {
      return nam;
   }

   public void setNam(String nam) {
      this.nam = nam;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      CustomerEntity customerEntity = (CustomerEntity) o;

      if (id != customerEntity.id) return false;
      if (nam != null ? !nam.equals(customerEntity.nam) : customerEntity.nam != null) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = id;
      result = 31 * result + (nam != null ? nam.hashCode() : 0);
      return result;
   }
}