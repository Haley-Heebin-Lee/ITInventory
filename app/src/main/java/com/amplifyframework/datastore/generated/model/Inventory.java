package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Inventory type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Inventories", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Inventory implements Model {
  public static final QueryField ID = field("Inventory", "id");
  public static final QueryField NAME = field("Inventory", "name");
  public static final QueryField AMOUNT = field("Inventory", "amount");
  public static final QueryField DESCRIPTION = field("Inventory", "description");
  public static final QueryField LOCATION = field("Inventory", "location");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int", isRequired = true) Integer amount;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String location;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
    private boolean expanded;

    public boolean isExpanded()
    {
        return expanded;
    }
    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getAmount() {
      return amount;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getLocation() {
      return location;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Inventory(String id, String name, Integer amount, String description, String location) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.description = description;
    this.location = location;
    this.expanded = false;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Inventory inventory = (Inventory) obj;
      return ObjectsCompat.equals(getId(), inventory.getId()) &&
              ObjectsCompat.equals(getName(), inventory.getName()) &&
              ObjectsCompat.equals(getAmount(), inventory.getAmount()) &&
              ObjectsCompat.equals(getDescription(), inventory.getDescription()) &&
              ObjectsCompat.equals(getLocation(), inventory.getLocation()) &&
              ObjectsCompat.equals(getCreatedAt(), inventory.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), inventory.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getAmount())
      .append(getDescription())
      .append(getLocation())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Inventory {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("amount=" + String.valueOf(getAmount()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Inventory justId(String id) {
    return new Inventory(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      amount,
      description,
      location);
  }
  public interface NameStep {
    AmountStep name(String name);
  }
  

  public interface AmountStep {
    LocationStep amount(Integer amount);
  }
  

  public interface LocationStep {
    BuildStep location(String location);
  }
  

  public interface BuildStep {
    Inventory build();
    BuildStep id(String id);
    BuildStep description(String description);
  }
  

  public static class Builder implements NameStep, AmountStep, LocationStep, BuildStep {
    private String id;
    private String name;
    private Integer amount;
    private String location;
    private String description;
    @Override
     public Inventory build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Inventory(
          id,
          name,
          amount,
          description,
          location);
    }
    
    @Override
     public AmountStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public LocationStep amount(Integer amount) {
        Objects.requireNonNull(amount);
        this.amount = amount;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer amount, String description, String location) {
      super.id(id);
      super.name(name)
        .amount(amount)
        .location(location)
        .description(description);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder amount(Integer amount) {
      return (CopyOfBuilder) super.amount(amount);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
