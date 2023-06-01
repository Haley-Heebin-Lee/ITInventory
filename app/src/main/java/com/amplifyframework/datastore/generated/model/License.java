package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the License type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Licenses", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class License implements Model {
  public static final QueryField ID = field("License", "id");
  public static final QueryField EXPIRY = field("License", "expiry");
  public static final QueryField ACCOUNT_WIDE = field("License", "accountWide");
  public static final QueryField NAME = field("License", "name");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSDate", isRequired = true) Temporal.Date expiry;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean accountWide;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="User") @HasMany(associatedWith = "licenseID", type = User.class) List<User> users = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Temporal.Date getExpiry() {
      return expiry;
  }
  
  public Boolean getAccountWide() {
      return accountWide;
  }
  
  public String getName() {
      return name;
  }
  
  public List<User> getUsers() {
      return users;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private License(String id, Temporal.Date expiry, Boolean accountWide, String name) {
    this.id = id;
    this.expiry = expiry;
    this.accountWide = accountWide;
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      License license = (License) obj;
      return ObjectsCompat.equals(getId(), license.getId()) &&
              ObjectsCompat.equals(getExpiry(), license.getExpiry()) &&
              ObjectsCompat.equals(getAccountWide(), license.getAccountWide()) &&
              ObjectsCompat.equals(getName(), license.getName()) &&
              ObjectsCompat.equals(getCreatedAt(), license.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), license.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getExpiry())
      .append(getAccountWide())
      .append(getName())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("License {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("expiry=" + String.valueOf(getExpiry()) + ", ")
      .append("accountWide=" + String.valueOf(getAccountWide()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ExpiryStep builder() {
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
  public static License justId(String id) {
    return new License(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      expiry,
      accountWide,
      name);
  }
  public interface ExpiryStep {
    AccountWideStep expiry(Temporal.Date expiry);
  }
  

  public interface AccountWideStep {
    NameStep accountWide(Boolean accountWide);
  }
  

  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    License build();
    BuildStep id(String id);
  }
  

  public static class Builder implements ExpiryStep, AccountWideStep, NameStep, BuildStep {
    private String id;
    private Temporal.Date expiry;
    private Boolean accountWide;
    private String name;
    @Override
     public License build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new License(
          id,
          expiry,
          accountWide,
          name);
    }
    
    @Override
     public AccountWideStep expiry(Temporal.Date expiry) {
        Objects.requireNonNull(expiry);
        this.expiry = expiry;
        return this;
    }
    
    @Override
     public NameStep accountWide(Boolean accountWide) {
        Objects.requireNonNull(accountWide);
        this.accountWide = accountWide;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
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
    private CopyOfBuilder(String id, Temporal.Date expiry, Boolean accountWide, String name) {
      super.id(id);
      super.expiry(expiry)
        .accountWide(accountWide)
        .name(name);
    }
    
    @Override
     public CopyOfBuilder expiry(Temporal.Date expiry) {
      return (CopyOfBuilder) super.expiry(expiry);
    }
    
    @Override
     public CopyOfBuilder accountWide(Boolean accountWide) {
      return (CopyOfBuilder) super.accountWide(accountWide);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
