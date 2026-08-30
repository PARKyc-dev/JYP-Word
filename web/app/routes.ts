import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("study", "routes/study.tsx"),
  route("todo", "todo/routes/todo.tsx"),
] satisfies RouteConfig;
