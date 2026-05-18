# MCP Bridge Example

TouchPilot can consume external MCP tools through the in-app MCP client. The
Phase 4 MVP supports HTTP JSON-RPC endpoints for:

- `initialize`
- `tools/list`
- `tools/call`

## Android App Client

1. Start an MCP server that exposes an HTTP JSON-RPC endpoint.
2. Open TouchPilot.
3. Enter the endpoint in the MCP Client section.
4. Tap `List MCP Tools` to initialize the session and inspect available tools.
5. Enter a tool name and JSON arguments, then tap `Call MCP Tool`.

Example arguments:

```json
{
  "query": "Android accessibility automation"
}
```

## Desktop-Agent Direction

Desktop agents can call the same external MCP server directly, while TouchPilot
continues to own Android-native actions through its local tool layer. This keeps
phone-control permissions and approvals inside Android, and uses MCP for
interoperability with external services.

## Server Direction

Exposing Android tools as an MCP server is intentionally deferred from this MVP.
That needs foreground-service lifecycle management, authentication, network
binding controls, and a user-visible permission model before it should be
enabled on a phone.
