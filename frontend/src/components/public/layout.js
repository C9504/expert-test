import React from "react";
import { Link, Outlet } from "react-router-dom";

const Layout = () => {

    return (
        <>
            <nav className="navbar navbar-expand-lg bg-body-tertiary mb-5">
                <div className="container-fluid">
                    <Link className="navbar-brand" href="#">Expert Medical</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarScroll">
                        <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style={{ "--bs-scroll-height": "100px" }}>
                            <li className="nav-item">
                                <Link className="nav-link" aria-current="page" to={"/"}>Users</Link>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            <input disabled={true} className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
                            <button disabled={true} className="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
            <main className="container">
                <Outlet />
            </main>
            <footer id="footer" className="footer mt-5 text-center fixed-bottom">
                <div className="copyright">
                    &copy; Copyright <strong><span>Cesar Pérez</span></strong>. All Rights Reserved
                </div>
                <div className="credits">
                    Adapted by <a href="#">Expert Medical</a>
                </div>
            </footer>
        </>
    );
}
export default Layout;