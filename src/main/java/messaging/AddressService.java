package messaging;

public class AddressService {

    private Address frontendAddress;
    private Address loginServletAddress;
    private Address signupServletAddress;
    private Address accountServiceAddress;

    public Address getFrontendAddress() {
        return frontendAddress;
    }

    public void setFrontendAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }

    public Address getLoginServletAddress() {
        return loginServletAddress;
    }

    public void setLoginServletAddress(Address loginServletAddress) {
        this.loginServletAddress = loginServletAddress;
    }

    public Address getSignupServletAddress() {
        return signupServletAddress;
    }

    public void setSignupServletAddress(Address signupServletAddress) {
        this.signupServletAddress = signupServletAddress;
    }

    public Address getAccountServiceAddress() {
        return accountServiceAddress;
    }

    public void setAccountServiceAddress(Address accountServiceAddress) {
        this.accountServiceAddress = accountServiceAddress;
    }
}
