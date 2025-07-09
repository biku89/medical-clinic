import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Patient {
    private String fistName;
    private String lastName;
    private String email;
    private String password;
    private int idCardNo;
    private String phoneNumber;
    private String birthday;

}
