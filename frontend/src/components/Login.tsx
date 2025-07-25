import { useMsal } from "@azure/msal-react";
import { loginRequest } from "../config/authConfig";

export const SignInButton = () => {
  const { instance } = useMsal();

  const handleLogin = () => {
    instance.loginRedirect(loginRequest);
  };

  return <button onClick={handleLogin}>Login with Microsoft</button>;
};
