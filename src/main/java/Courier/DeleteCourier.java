package courier;

import lombok.*;

@Data
@Setter
@Getter
public class DeleteCourier {
    private String id;

    public DeleteCourier(String id) {
        this.id = id;
    }

    public DeleteCourier() {

    }
}
