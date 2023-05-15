import {createRouter, createWebHistory} from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: "/login",
        children: [
            {
                path: 'user',
                name: 'User',
                component: () => import("../views/User"),
            },
            {
                path: '/userinfor',
                name: 'UserInfor',
                component: () => import("../views/UserInfor"),
            },
            {
                path: '/travel',
                name: 'Travel',
                component: () => import("../views/Travel"),
            },
            {
                path: '/news',
                name: 'News',
                component: () => import("../views/News"),
            }

        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("../views/Login"),
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
