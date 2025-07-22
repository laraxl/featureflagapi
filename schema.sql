-- USERS
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL
);

-- TEAMS
CREATE TABLE teams (
    name TEXT PRIMARY KEY
);

-- TEAM MEMBERSHIP
CREATE TABLE team_members (
    team_name TEXT REFERENCES teams(name) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role TEXT,
    PRIMARY KEY (team_name, user_id)
);

-- FEATURE FLAGS
CREATE TABLE feature_flags (
    name TEXT PRIMARY KEY,
    description TEXT,
    status TEXT CHECK (status IN ('enabled', 'disabled')),
    version TEXT,
    team_name TEXT REFERENCES teams(name) ON DELETE SET NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now()
);

-- ENVIRONMENTS
CREATE TABLE environments (
    name TEXT PRIMARY KEY,
    type TEXT CHECK (type IN ('dev', 'staging', 'prod'))
);

-- ENVIRONMENT FLAG STATUS
CREATE TABLE environment_flag_status (
    env_name TEXT REFERENCES environments(name) ON DELETE CASCADE,
    flag_name TEXT REFERENCES feature_flags(name) ON DELETE CASCADE,
    status TEXT CHECK (status IN ('enabled', 'disabled')),
    PRIMARY KEY (env_name, flag_name)
);

-- SCHEDULES
CREATE TABLE flag_schedules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    flag_name TEXT REFERENCES feature_flags(name) ON DELETE CASCADE,
    scheduled_time TIMESTAMPTZ NOT NULL,
    action TEXT CHECK (action IN ('enable', 'disable'))
);

-- AUDIT LOG
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    flag_name TEXT REFERENCES feature_flags(name),
    changed_by UUID REFERENCES users(id),
    timestamp TIMESTAMPTZ DEFAULT now(),
    change TEXT
);

-- TOGGLE DEPENDENCIES
CREATE TABLE toggle_dependencies (
    source TEXT REFERENCES feature_flags(name),
    depends_on TEXT REFERENCES feature_flags(name),
    PRIMARY KEY (source, depends_on)
);

-- TOGGLE VARIATIONS
CREATE TABLE toggle_variations (
    flag_name TEXT REFERENCES feature_flags(name),
    variation TEXT,
    weight NUMERIC CHECK (weight >= 0 AND weight <= 1),
    PRIMARY KEY (flag_name, variation)
);

-- TOGGLE RULES
CREATE TABLE toggle_rules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    flag_name TEXT REFERENCES feature_flags(name),
    condition TEXT,
    outcome TEXT
);
