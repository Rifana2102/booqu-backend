import React from "react";
import Onboarding from "./components/Onboarding";

function App() {
  return (
    <div>
      <div style={{ color: "#ccc", fontSize: "1.2rem", margin: "1rem 0 0 1rem" }}>On boarding</div>
      <div style={{ color: "#fff", fontSize: "2rem", marginLeft: "1rem" }}>Booqu</div>
      <Onboarding />
    </div>
  );
}

export default App;